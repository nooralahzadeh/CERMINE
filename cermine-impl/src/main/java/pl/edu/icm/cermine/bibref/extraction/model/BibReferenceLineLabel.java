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

package pl.edu.icm.cermine.bibref.extraction.model;

/**
 * Bibliographic reference line label.
 *
 * @author Dominika Tkaczyk (dtkaczyk@icm.edu.pl)
 */
public enum BibReferenceLineLabel {

    /** References block title line, eg. "References" */
    BLOCK_LABEL,
    /** The first line of the reference (even if the only line) **/
    BIBREF_START,
    /** The inner line of the reference (if the reference has at least three lines) */
    BIBREF_INNER,
    /* The last line of the reference (if the reference has at leat two lines) */
    BIBREF_END,

}
