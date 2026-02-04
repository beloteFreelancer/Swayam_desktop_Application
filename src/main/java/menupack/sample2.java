package menupack;

import javax.swing.table.DefaultTableModel;

public class sample2 extends DefaultTableModel {

    @Override
    public void addColumn(Object columnName) {
        super.addColumn(columnName);
    }

    @Override
    public void addRow(Object[] rowData) {
        super.addRow(rowData);
    }
}
