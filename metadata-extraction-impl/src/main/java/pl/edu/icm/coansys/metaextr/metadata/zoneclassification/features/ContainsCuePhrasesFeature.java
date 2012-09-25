package pl.edu.icm.coansys.metaextr.metadata.zoneclassification.features;

import pl.edu.icm.coansys.metaextr.classification.features.FeatureCalculator;
import pl.edu.icm.coansys.metaextr.textr.model.BxChunk;
import pl.edu.icm.coansys.metaextr.textr.model.BxLine;
import pl.edu.icm.coansys.metaextr.textr.model.BxPage;
import pl.edu.icm.coansys.metaextr.textr.model.BxWord;
import pl.edu.icm.coansys.metaextr.textr.model.BxZone;

public class ContainsCuePhrasesFeature implements
		FeatureCalculator<BxZone, BxPage> {

	private static String featureName = "ContainsCuePhrases";
	private static String[] cuePhrases = {"although", "therefore", "therein", "hereby",
			"nevertheless", "to this end", "however", "moreover", "nonetheless" };

	@Override
	public String getFeatureName() {
		return featureName;
	}

	@Override
	public double calculateFeatureValue(BxZone zone, BxPage page) {
		String zoneText = zone.toText().toLowerCase();
		int count = 0;
		
		for(String cuePhrase: cuePhrases) {
			if(!zoneText.contains(cuePhrase))
				continue;
			else
				return 1.0;
		}
		return 0.0;
	}
}