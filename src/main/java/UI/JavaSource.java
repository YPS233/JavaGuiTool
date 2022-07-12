package UI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

public class JavaSource implements MouseListener {

    Vector<String> leftData;
    Vector<String> rightData;
    JList<String> left;

    JTextPane right;
    JScrollPane leftPanel, rightPanel;

    Map<String,String> data;

    public JavaSource(Map<String,String> data) throws HeadlessException {

        this.data = data;

        ArrayList<String> keys = new ArrayList<>();
        for(String s: data.keySet()){
            keys.add(s);
        }


        JDialog frame = new JDialog();
        frame.setTitle("Java Source Code");
        frame.setVisible(true);
        frame.setSize(800,600);

        leftData = new Vector<>(keys);
        left = new JList<>(leftData);
        leftPanel = new JScrollPane(left);

        rightData = new Vector<>();
        right = new JTextPane();
        rightPanel = new JScrollPane(right);

        frame.setLayout(new GridLayout(1, 4));
        frame.add(leftPanel);
        frame.add(rightPanel);

        left.addListSelectionListener(new ListSelectionListener() {
          @Override
          public void valueChanged(ListSelectionEvent e) {
              // 获取所有被选中的选项索引
              int[] indices = left.getSelectedIndices();
              // 获取选项数据的 ListModel
              ListModel<String> listModel = left.getModel();

              String currentSelect = null;
              // 输出选中的选项
              for (int index : indices) {
                  currentSelect = listModel.getElementAt(index);
//                  System.out.println("选中: " + index + " = " + listModel.getElementAt(index));
              }
//              System.out.println(data.get(currentSelect));
              String showCode = data.get(currentSelect);
//              left.setListData(data.get(currentSelect));
              right.setText(showCode);
          }
      });


    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    private void doubleClick(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
