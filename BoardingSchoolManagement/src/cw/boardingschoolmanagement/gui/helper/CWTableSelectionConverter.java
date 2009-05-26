package cw.boardingschoolmanagement.gui.helper;

import com.jgoodies.binding.value.AbstractConverter;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.gui.component.CWTable;


public class CWTableSelectionConverter extends AbstractConverter {

    private final CWTable table;

    public CWTableSelectionConverter(final ValueModel selectionIndexHolder, final CWTable table) {
        super(selectionIndexHolder);
        this.table = table;
    }

    public Object convertFromSubject(Object subjectValue) {
        int viewIndex = -1;
        int modelIndex = -1;

        if (subjectValue != null) {
            modelIndex = ((Integer) subjectValue).intValue();
            if (modelIndex >= 0) {
                viewIndex = table.convertRowIndexToView(modelIndex);
            }
        }
        return new Integer(viewIndex);
    }

    public void setValue(Object newValue) {
        int viewIndex = -1;
        int modelIndex = -1;

        if (newValue != null) {
            viewIndex = ((Integer) newValue).intValue();
            if (viewIndex >= 0) {
                modelIndex = table.convertRowIndexToModel(viewIndex);
            }
        }
        subject.setValue(new Integer(modelIndex));
    }
}