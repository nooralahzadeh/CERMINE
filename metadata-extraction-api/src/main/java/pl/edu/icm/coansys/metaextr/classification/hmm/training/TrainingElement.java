package pl.edu.icm.coansys.metaextr.classification.hmm.training;

import pl.edu.icm.coansys.metaextr.classification.features.FeatureVector;
import pl.edu.icm.coansys.metaextr.textr.model.BxZoneLabel;

/**
 * Training element interface for object classification.
 *
 * @author Dominika Tkaczyk (dtkaczyk@icm.edu.pl)
 * @param <S> Type of labels of objects.
 */
public interface TrainingElement<S> {

    /**
     * Gets observation emitted by an element.
     *
     * @return Object's observation.
     */
    FeatureVector getObservation();

    /**
     * Checks whether an element is first in the sequence.
     *
     * @return true if element is first, false otherwise
     */
    boolean isFirst();

    /**
     * Gets label of an element.
     *
     * @return The label.
     */
    S getLabel();
    

    /**
     * Gets the label of the following element, or null if the element is the
     * last in the sequence.
     *
     * @return The label of the following element.
     */
    S getNextLabel();
    public TrainingElement<S> clone();


}