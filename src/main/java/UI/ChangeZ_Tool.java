package UI;

import Util.JavaUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class ChangeZ_Tool {
    private JTextArea textAreaBase64;
    private JButton Button_toclass;
    private JPanel root;
    private JButton button_tojava;

    public ChangeZ_Tool() {
        textAreaBase64.setLineWrap(true);        //激活自动换行功能
        textAreaBase64.setWrapStyleWord(true);            // 激活断行不断字功能

        Button_toclass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String base64Data = textAreaBase64.getText();
                System.out.println(base64Data);
                byte[] b = Base64.getDecoder().decode(base64Data);

                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = chooser.showOpenDialog(Button_toclass);
                if (returnVal == JFileChooser.APPROVE_OPTION) {          //如果符合文件类型

                    String dirPath = chooser.getSelectedFile().getAbsolutePath();      //获取绝对路径
                    String filepath = dirPath + File.separator + "res.class";
                    System.out.println(filepath);
                    File saveFile = new File(filepath);
                    try {
                        FileOutputStream fos = new FileOutputStream(saveFile);
                        fos.write(b);
                        fos.flush();
                        fos.close();
                        JOptionPane.showMessageDialog(root, "保存成功，路径为:" + filepath);

                    }catch (IOException exception){
                        exception.printStackTrace();
                    }

                    System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());  //输出相对路径
                }else {
                    System.out.println("not a dir path");
                }



                }
        });
        button_tojava.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String base64Data = textAreaBase64.getText();
                Map<String,String> javaMap = null;
                try {
                    javaMap = JavaUtil.base64Tojava(base64Data);
                }catch (IllegalArgumentException ei){
                    JOptionPane.showMessageDialog(null, "非法的base64字符串", "错误", JOptionPane.INFORMATION_MESSAGE);

                }
                catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                JavaSource js = new JavaSource(javaMap);

            }
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("ChangeZ_Tool");
        frame.setContentPane(new ChangeZ_Tool().root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);
        //frame.pack();
        frame.setVisible(true);
    }
}
