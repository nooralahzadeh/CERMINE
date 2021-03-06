/**
 * This file is part of CERMINE project.
 * Copyright (c) 2011-2013 ICM-UW
 *
 * CERMINE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CERMINE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with CERMINE. If not, see <http://www.gnu.org/licenses/>.
 */
package pl.edu.icm.cermine.libsvm.parameters;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import libsvm.svm_parameter;
import org.apache.commons.cli.ParseException;
import pl.edu.icm.cermine.exception.AnalysisException;
import pl.edu.icm.cermine.exception.TransformationException;
import pl.edu.icm.cermine.structure.SVMInitialZoneClassifier;
import pl.edu.icm.cermine.structure.model.BxPage;
import pl.edu.icm.cermine.structure.model.BxZone;
import pl.edu.icm.cermine.structure.model.BxZoneLabel;
import pl.edu.icm.cermine.tools.BxDocUtils.DocumentsIterator;
import pl.edu.icm.cermine.tools.classification.general.BxDocsToTrainingSamplesConverter;
import pl.edu.icm.cermine.tools.classification.general.FeatureVectorBuilder;
import pl.edu.icm.cermine.tools.classification.general.PenaltyCalculator;
import pl.edu.icm.cermine.tools.classification.general.TrainingSample;
import pl.edu.icm.cermine.tools.classification.svm.SVMZoneClassifier;

/**
 * @author Dominika Tkaczyk
 */
public class InitialClassifierParameterFinder extends SVMParameterFinder {

    @Override
    protected SVMZoneClassifier getZoneClassifier(List<TrainingSample<BxZoneLabel>> trainingSamples, int kernelType, double gamma, double C, int degree) throws IOException, AnalysisException, CloneNotSupportedException {
        for (TrainingSample<BxZoneLabel> trainingSample : trainingSamples) {
            trainingSample.setLabel(trainingSample.getLabel().getGeneralLabel());
        }

        PenaltyCalculator pc = new PenaltyCalculator(trainingSamples);
        int[] intClasses = new int[pc.getClasses().size()];
        double[] classesWeights = new double[pc.getClasses().size()];

        int labelIdx = 0;
        for (BxZoneLabel label : pc.getClasses()) {
            intClasses[labelIdx] = label.ordinal();
            classesWeights[labelIdx] = pc.getPenaltyWeigth(label);
            ++labelIdx;
        }

        SVMZoneClassifier zoneClassifier = new SVMZoneClassifier(SVMInitialZoneClassifier.getFeatureVectorBuilder());
        svm_parameter param = SVMZoneClassifier.getDefaultParam();
        param.svm_type = svm_parameter.C_SVC;
        param.gamma = gamma;
        param.C = C;
        System.out.println(degree);
        param.degree = degree;
        param.kernel_type = kernelType;
        param.weight = classesWeights;
        param.weight_label = intClasses;

        zoneClassifier.setParameter(param);
        zoneClassifier.buildClassifier(trainingSamples);

        return zoneClassifier;
    }

    public static void main(String[] args)
            throws ParseException, AnalysisException, IOException, TransformationException, CloneNotSupportedException, InterruptedException, ExecutionException {
        SVMParameterFinder.main(args, new InitialClassifierParameterFinder());
    }

    @Override
    protected FeatureVectorBuilder<BxZone, BxPage> getFeatureVectorBuilder() {
        return SVMInitialZoneClassifier.getFeatureVectorBuilder();
    }

    @Override
    public List<TrainingSample<BxZoneLabel>> getSamples(String inputFile, String ext) throws AnalysisException {
        DocumentsIterator it = new DocumentsIterator(inputFile, ext);
        return BxDocsToTrainingSamplesConverter.getZoneTrainingSamples(it.iterator(),
                getFeatureVectorBuilder(),
                BxZoneLabel.getLabelToGeneralMap());
    }
}
