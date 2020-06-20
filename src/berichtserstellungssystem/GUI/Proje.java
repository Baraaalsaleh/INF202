//Baraa Alsaleh, 19050800
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.GUI;

import berichtserstellungssystem.DatabaseManagement.DatabaseManagement;
import berichtserstellungssystem.DatabaseManagement.OthersManagement;
import berichtserstellungssystem.Resource.Manager;
import berichtserstellungssystem.Verification;
import java.awt.Color;
import java.sql.SQLException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Baraa
 */
public class Proje extends javax.swing.JFrame {

    int type;
    int process;
    String name;
    Manager me;
    /**
     * Creates new form Proje
     */
    public Proje() {
        initComponents();
    }
    
    public Proje(int type, int process, String name, Manager manager) {
        this.type = type;
        this.process = process;
        this.name = name;
        this.me = manager;
        initComponents();
        setEveryThing();
    }
    
    private void setEveryThing() {
        if (type == 1) {
            jLabel1.setText("Proje");
            jTextField1.setText("Proje");
        }
        else if (type == 2) {
            jLabel1.setText("Yüzay Durumu");
            jTextField1.setText("Yüzay Durumu");
        }
        else if (type == 3) {
            jLabel1.setText("Muayene Aşaması");
            jTextField1.setText("Muayene Aşaması");
        }
        if (process == 1) {
            jLabel4.setVisible(false);
        }
        if (process == 2) {
            jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/update.png")));
            jTextField1.setText(name);
        }
        jLabel3.setEnabled(false);
    }
    
    private boolean everyThingIsOkay() {
        if (type == 1) {
            if (jTextField1.getText().length() > 2 && !jTextField1.getText().trim().equals("Proje") && OthersManagement.checkProjectName(jTextField1.getText().trim())) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (type == 2) {
            if (jTextField1.getText().length() > 2 && !jTextField1.getText().trim().equals("Yuzay Durumu") && OthersManagement.checkSurfaceCondition(jTextField1.getText().trim())) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            if (jTextField1.getText().length() > 2 && !jTextField1.getText().trim().equals("Muayene Aşaması") && OthersManagement.checkStageOfExamination(jTextField1.getText().trim())) {
                return true;
            }
            else {
                return false;
            }
        }
    }
    
    private void everyThingIsOkay(int a) {
        if (type == 1) {
            jTextField1.setBackground(Color.pink);
            if (jTextField1.getText().trim().equals("") || jTextField1.getText().trim().equals("Proje")) {
                jTextField1.setToolTipText("Zorunlu alan!");
            }
            else if (jTextField1.getText().length() < 3 || jTextField1.getText().length() > 64) {
                jTextField1.setToolTipText("Proje adı 2 - 64 harften oluşabilir!");
            }
            else if (!OthersManagement.checkProjectName(jTextField1.getText().trim()) && process == 1) {
                jTextField1.setToolTipText("Girdiğiniz proje adının veri tabanında bulunduğu için kullanılmaz! Farklı bir proje adı giriniz!");
            }
            else if (!OthersManagement.checkProjectName(jTextField1.getText().trim()) && process == 2 && jTextField1.getText().trim().equals(name)) {
                jTextField1.setToolTipText("Bir değişiklik yapılmadı!");
            }
            else {
                jTextField1.setBackground(Color.white);
                jTextField1.setToolTipText(null);
            }
        }
        else if (type == 2) {
            jTextField1.setBackground(Color.pink);
            if (jTextField1.getText().trim().equals("") || jTextField1.getText().trim().equals("Yuzay Durumu")) {
                jTextField1.setToolTipText("Zorunlu alan!");
            }
            else if (jTextField1.getText().length() < 3 || jTextField1.getText().length() > 64) {
                jTextField1.setToolTipText("Yuzay durumu 2 - 64 harften oluşabilir!");
            }
            else if (!OthersManagement.checkSurfaceCondition(jTextField1.getText().trim()) && process == 1) {
                jTextField1.setToolTipText("Girdiğiniz yuzay durumunun veri tabanında bulunduğu için kullanılmaz! Farklı bir yuzay durumu giriniz!");
            }
            else if (!OthersManagement.checkSurfaceCondition(jTextField1.getText().trim()) && process == 2 && jTextField1.getText().trim().equals(name)) {
                jTextField1.setToolTipText("Bir değişiklik yapılmadı!");
            }
            else {
                jTextField1.setBackground(Color.white);
                jTextField1.setToolTipText(null);
            }
        }
        else {
            jTextField1.setBackground(Color.pink);
            if (jTextField1.getText().trim().equals("") || jTextField1.getText().trim().equals("Muayene Aşaması")) {
                jTextField1.setToolTipText("Zorunlu alan!");
            }
            else if (jTextField1.getText().length() < 3 || jTextField1.getText().length() > 64) {
                jTextField1.setToolTipText("Muayene aşaması 2 - 64 harften oluşabilir!");
            }
            else if (!OthersManagement.checkStageOfExamination(jTextField1.getText().trim()) && process == 1) {
                jTextField1.setToolTipText("Girdiğiniz muayene aşaması veri tabanında bulunduğu için kullanılmaz! Farklı bir muayene aşaması giriniz!");
            }
            else if (!OthersManagement.checkStageOfExamination(jTextField1.getText().trim()) && process == 2 && jTextField1.getText().trim().equals(name)) {
                jTextField1.setToolTipText("Bir değişiklik yapılmadı!");
            }
            else {
                jTextField1.setBackground(Color.white);
                jTextField1.setToolTipText(null);
            }
        }
    }
    
    private void cleanAll() {
        if (type == 1) {
            jTextField1.setText("Proje");
        }
        else if (type == 2) {
            jTextField1.setText("Yüzay Durumu");
        }
        else if (type == 3) {
            jTextField1.setText("Muayene Aşaması");
        }
        jLabel3.setEnabled(false);
    }
    
    
    private void massege (int done) {
        JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        String func = "Ekleme";
        if (process == 2) {
            func = "Güncelleme";
        }
        else if (process == 3) {
            func = "Silme";
        }
        if (done == 1) {
            JOptionPane.showMessageDialog(dialog, func  + " işlemi başarıyla tamamlanmıştır", "Başarılı İşlem", JOptionPane.PLAIN_MESSAGE);
            if (process == 1 || process == 3) {
                cleanAll();
            }
        }
        else if (done == 0) {
            JOptionPane.showMessageDialog(dialog, "Girdiğiniz " + jLabel1.getText() + " daha önce veri tabanında bulunduğu için kullanılmaz!", "Hatalı İşlem", JOptionPane.PLAIN_MESSAGE);
            if (process == 3) { process = 2;}
        }
        else {
            JOptionPane.showMessageDialog(dialog, "Veri tabanına bağlanırken hata oluştu!, Lütfen tekrar deneyin.", "Hatalı Bağlantı", JOptionPane.PLAIN_MESSAGE);
            try {
                DatabaseManagement.con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
            DatabaseManagement.con = DatabaseManagement.connect();
            if (process == 3) { process = 2;}
        }
    }
    
    
    private void add() {
        int res;
        if (type == 1) {
            res = OthersManagement.insertProject(jTextField1.getText().trim());
        }
        else if (type == 2) {
            res = OthersManagement.insertSurfaceCondition(jTextField1.getText().trim());
        }
        else {
            res = OthersManagement.insertStageOfExamination(jTextField1.getText().trim());
        }
        massege(res);
    }
    
    private void update() {
        int res;
        if (type == 1) {
            res = OthersManagement.updateProject(name, jTextField1.getText().trim());
        }
        else if (type == 2) {
            res = OthersManagement.updateSurfaceCondition(name, jTextField1.getText().trim());
        }
        else {
            res = OthersManagement.updateStageOfExamination(name, jTextField1.getText().trim());
        }
        massege(res);
    }

    void delete() {
        int res;
        if (type == 1) {
            res = OthersManagement.deleteProject(name);
        }
        else if (type == 2) {
            res = OthersManagement.deleteSurfaceCondition(name);
        }
        else {
            res = OthersManagement.deleteStageOfExamination(name);
        }
        massege(res);
        this.dispose();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setMaximumSize(new java.awt.Dimension(434, 207));
        setMinimumSize(new java.awt.Dimension(434, 207));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setBackground(new java.awt.Color(153, 51, 255));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Title");
        jLabel1.setOpaque(true);

        jTextField1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1FocusGained(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/add.png"))); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/minus.png"))); // NOI18N
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(113, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(96, 96, 96)
                .addComponent(jLabel3)
                .addGap(113, 113, 113))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
    }//GEN-LAST:event_formWindowOpened

    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusGained
        jTextField1.selectAll();
    }//GEN-LAST:event_jTextField1FocusGained

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        if (everyThingIsOkay()){
            jLabel3.setEnabled(true);
        }
        else {
            jLabel3.setEnabled(false);
        }
        everyThingIsOkay(1);
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        if (jLabel3.isEnabled()) {
            if (process == 1) {
                add();
            }
            else {
                update();
            }
        }
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        process = 3;
        new Verify(name + " silmekten emin misiniz?", this, type+4).setVisible(true);
    }//GEN-LAST:event_jLabel4MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Proje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Proje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Proje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Proje.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Proje().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
