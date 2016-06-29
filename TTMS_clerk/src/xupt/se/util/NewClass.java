package src.xupt.se.util;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Created by kiosk on 6/25/16.
 */
public class NewClass {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        final JTable table = new JTable(4, 4) {

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                if (row == 1 && column == 1) {
                    return new DefaultCellEditor(new JCheckBox());
                }
                return super.getCellEditor(row, column);
            }

            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (row == 1 && column == 1) {
                    return new TableCellRenderer() {

                        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                            return new JCheckBox();
                        }
                    };
                }
                return super.getCellRenderer(row, column);
            }
        };
        frame.getContentPane().add(table, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }
}
