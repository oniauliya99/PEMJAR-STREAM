/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package praktikum2_2;

import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.nio.file.Files.list;
import static java.rmi.Naming.list;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;


public class Praktikum2Controller {
    
    private List<Integer> list = new ArrayList<>();
    private Praktikum2_2 view;
    
        public Praktikum2Controller(Praktikum2_2 view) {
            this.view = view;
            this.view.getBtn_baca().addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                proses();
            }
        });
            this.view.getBtn_simpan().addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 save();
             }
         });
    }
        
        private void proses() {
            JFileChooser loadFile = view.getLoadFile();
            StyledDocument doc = view.getTxtPane().getStyledDocument();
            if (JFileChooser.APPROVE_OPTION == loadFile.showOpenDialog(view)) {
                InputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(loadFile.getSelectedFile());
                    int read = inputStream.read();
                    doc.insertString(0, "", null);
                    while (read != -1) {
                        list.add(read);
                        doc.insertString(doc.getLength(), "" + read, null);
                        System.out.println("" + read);
                        read = inputStream.read();
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Praktikum2Controller.class.getName()).log(Level.SEVERE, null, ex);
                }catch (IOException | BadLocationException ex){
                    Logger.getLogger(Praktikum2Controller.class.getName()).log(Level.SEVERE, null, ex);
                }finally{
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException ex) {
                            Logger.getLogger(Praktikum2Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        
        private void save() {
         JFileChooser loadFile = view.getLoadFile();
         if (JFileChooser.APPROVE_OPTION == loadFile.showSaveDialog(view)) {
             OutputStream os = null;
             try {
                 if (!list.isEmpty()) {
                     os = new FileOutputStream(loadFile.getSelectedFile());
                     byte[] dt = new byte[list.size()];
                     for (int i = 0; i < list.size(); i++) {
                         dt[i] = list.get(i).byteValue();
                     }
                     os.write(dt);
                 }
             } catch (FileNotFoundException ex) {
                 Logger.getLogger(Praktikum2Controller.class.getName()).log(Level.SEVERE, null, ex);
             } catch (IOException ex) {
                 Logger.getLogger(Praktikum2Controller.class.getName()).log(Level.SEVERE, null, ex);
             } finally {
                 if (os != null) {
                     try {
                         os.flush();
                         os.close();
                         list.clear();
                     } catch (IOException ex) {
                         Logger.getLogger(Praktikum2Controller.class.getName()).log(Level.SEVERE, null, ex);
                     }
                 }
             }
         }
     }
}
