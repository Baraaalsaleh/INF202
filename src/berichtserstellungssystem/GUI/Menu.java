/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.GUI;

import berichtserstellungssystem.Common;
import berichtserstellungssystem.DataPreparation;
import java.awt.Frame;
import berichtserstellungssystem.DatabaseManagement.*;
import berichtserstellungssystem.Resource.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.*;
import javax.swing.table.*;
/**
 *
 * @author Baraa
 */
public class Menu extends javax.swing.JFrame {

    int jPanel = 0;
    String[] personTable = new String[4];
    GridBagLayout layout = new GridBagLayout();
    //ReportList panel1;
    Manager me = new Manager();
    
    /**
     * Creates new form Menu
     */
    public Menu() {
        if (!PersonManagement.findAdmin()) {
            new Personel(0, 1, me).setVisible(true);
        }
        
        initComponents();
        /*
        panel1 = new ReportList();
        jPanel3.setLayout(layout);
        GridBagConstraints grid = new GridBagConstraints();
        grid.gridx = 0;
        grid.gridy = 0;
        jPanel3.add(panel1, grid);
        panel1.setVisible(false);
        */
    }
    
    private void downloadTemplates() {
        try {
            URL website = new URL("https://raw.githubusercontent.com/Baraaalsaleh/INF202/master/MagneticTemplate.xlsx");
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream("MagneticTemplate.xlsx");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
        catch (Exception e) {
            System.out.println("downloadTemplates " + e);
        }
    }
    
    public void isManager (boolean a){
        jMenuItem1.setVisible(!a);
        jMenuItem6.setVisible(a);
        jMenuItem7.setVisible(a);
        jMenuItem15.setVisible(a);
        jMenu3.setVisible(a);
        jMenu7.setVisible(a);
        
        if (me.getStatus() == 1) {
            jMenuItem11.setVisible(!a);
            jMenuItem25.setVisible(!a);
        }
        else {
            jMenuItem11.setVisible(a);
            jMenuItem25.setVisible(a);
        }
    }
    
    private void addEmployees (ArrayList<Employee> list, JTable t) {
        Object[] row = new Object[25];
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Adı");
        model.addColumn("Soyadı");
        model.addColumn("PNR");
        model.addColumn("Seviye");
        model.addColumn("İzinli");
        Date now = new Date();
        for (Employee e : list) {
            row[0] = e.getName();
            row[1] = e.getLastname();
            row[2] = e.getPersonalNr();
            row[3] = e.getLevel();
            if (now.before(e.getPermitionEndDate())) {
                row[4] = "Evet";
            }
            else {
                row[4] = "Hayır";
            }
            System.out.println(e.getName() + " " + e.getLastname());
            model.addRow(row);
        }
        t.setModel(model);
    }
    
    private void addManagers (ArrayList<Manager> list, JTable t) {
        Object[] row = new Object[25];
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Adı");
        model.addColumn("Soyadı");
        model.addColumn("PNR");
        for (Manager e : list) {
            row[0] = e.getName();
            row[1] = e.getLastname();
            row[2] = e.getPersonalNr();
            System.out.println(e.getName() + " " + e.getLastname());
            model.addRow(row);
        }
        t.setModel(model);
    }

    private void addEquipments (ArrayList<Equipment> list, JTable t) {
        Object[] row = new Object[25];
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Ekipman");
        model.addColumn("Tipi");
        model.addColumn("Kalibrasyon Son Geçerlilik Tarihi");
        model.addColumn("Geçerli");
        Date now = new Date();
        for (Equipment e : list) {
            row[0] = e.getName();
            if (e.getType() == DatabaseManagement.getMAGNETIC_TYPE()) {
                row[1] = "Manyetik";
            }
            else {
                row[1] = "Radyografik";
            }
            row[2] = e.getCalibrationEndDate();
            if (now.before(e.getCalibrationEndDate())) {
                row[3] = "Evet";
            }
            else {
                row[3] = "Hayır";
            }
            System.out.println(e.getName());
            model.addRow(row);
        }
        t.setModel(model);
    }
    
    private void addRows(String col, ArrayList<String> list, JTable table) {
        Object[] row = new Object[25];
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn(col);
        for (String s : list) {
            row[0] = s;
            model.addRow(row);
        }
        table.setModel(model);
    }
    
    private void addCustomers(ArrayList<Customer> list, JTable table) {
        Object[] row = new Object[25];
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Adı");
        model.addColumn("Adres");
        for (Customer e : list) {
            row[0] = e.getName();
            row[1] = e.getAddress();
            System.out.println(e.getName() + " " + e.getAddress());
            model.addRow(row);
        }
        table.setModel(model);
    }
    
    private void prepareTables (int table) {
        if (table == 1) {
                ArrayList<Employee> res = PersonManagement.employees(0, 25, 1, me);
                addEmployees(res, jTable2);
                res = PersonManagement.employees(0, 25, 2, me);
                addEmployees(res, jTable3);
                res = PersonManagement.employees(0, 25, 3, me);
                addEmployees(res, jTable4);
        }
        else if (table == 2) {
                ArrayList<Manager> res = PersonManagement.managers(0, 25);
                addManagers(res, jTable2);
        }
        else if (table == 3) {
                ArrayList<Equipment> res = EquipmentManagement.equipments(0, 25, 1, me);
                addEquipments(res, jTable2);
                res = EquipmentManagement.equipments(0, 25, 2, me);
                addEquipments(res, jTable3);
                res = EquipmentManagement.equipments(0, 25, 3, me);
                addEquipments(res, jTable4);
        }
        else if (table == 4) {
            ArrayList<String> res = OthersManagement.projects(0, 25);
            addRows("Proje", res, jTable2);
        }
        else if (table == 5) {
            ArrayList<String> res = OthersManagement.surfaceConditions(0, 25);
            addRows("Yüzay durumu", res, jTable2);
        }
        else if (table == 6) {
            ArrayList<String> res = OthersManagement.stageOfExaminations(0, 25);
            addRows("Muayene aşaması", res, jTable2);
        }
        else if (table == 7) {
            ArrayList<Customer> res = CustomerManagement.customers(0, 25, 1, me);
            addCustomers(res, jTable2);
            res = CustomerManagement.customers(0, 25, 2, me);
            addCustomers(res, jTable3);
            res = CustomerManagement.customers(0, 25, 3, me);
            addCustomers(res, jTable4);
        }
        
    }
    
    private void clearTables () {
        jTable2.setModel(null);
        jTable3.setModel(null);
        jTable4.setModel(null);
        
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Rapor Yönetimi");
        setBackground(new java.awt.Color(0, 0, 0));
        setFocusTraversalPolicyProvider(true);
        setLocation(new java.awt.Point(0, 0));
        setMaximumSize(new java.awt.Dimension(800, 500));
        setMinimumSize(new java.awt.Dimension(800, 500));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(800, 500));
        setResizable(false);
        setSize(new java.awt.Dimension(0, 0));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jScrollPane2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jTable2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable2);
        jTable2.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Tümü", jPanel1);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Eklediklerim", jPanel2);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTable4);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Düzelttiklerim", jPanel4);

        jScrollPane2.setViewportView(jTabbedPane1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                .addContainerGap())
        );

        jMenuBar1.setBackground(new java.awt.Color(0, 0, 102));
        jMenuBar1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jMenuBar1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jMenuBar1MouseDragged(evt);
            }
        });

        jMenu1.setText("Dosya");
        jMenu1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/minimize.png"))); // NOI18N
        jMenuItem5.setText("Küçült");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/exit.png"))); // NOI18N
        jMenuItem4.setText("Kapat");
        jMenuItem4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        jMenu6.setText("Rapor");
        jMenu6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setBackground(new java.awt.Color(204, 255, 204));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/report.png"))); // NOI18N
        jMenuItem2.setText("Yeni Rapor Oluştur");
        jMenuItem2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setBackground(new java.awt.Color(204, 255, 204));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/report.png"))); // NOI18N
        jMenuItem3.setText("Rapor Düzelt");
        jMenuItem3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem3);

        jMenuItem23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/continue.png"))); // NOI18N
        jMenuItem23.setText("Tüm Raporları Göster");
        jMenuItem23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu6.add(jMenuItem23);

        jMenuBar1.add(jMenu6);

        jMenu2.setText("Personel");
        jMenu2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/person.png"))); // NOI18N
        jMenuItem1.setText("Giriş Yap");
        jMenuItem1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/person.png"))); // NOI18N
        jMenuItem15.setText("Hesabım");
        jMenuItem15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem15);

        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/continue3.png"))); // NOI18N
        jMenuItem7.setText("Kaldığın yerden devam et");
        jMenuItem7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/out.png"))); // NOI18N
        jMenuItem6.setText("Çıkış Yap");
        jMenuItem6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Ekle");
        jMenu3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/person2.png"))); // NOI18N
        jMenuItem8.setText("Personel");
        jMenuItem8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem8);

        jMenuItem11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/person.png"))); // NOI18N
        jMenuItem11.setText("Personel Yönetici");
        jMenuItem11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem11);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/machine.png"))); // NOI18N
        jMenu4.setText("Ekipman");
        jMenu4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/magnetic3.png"))); // NOI18N
        jMenuItem19.setText("Manyetik");
        jMenuItem19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem19);

        jMenuItem24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/radiographic.png"))); // NOI18N
        jMenuItem24.setText("Radyografik");
        jMenuItem24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem24);

        jMenu3.add(jMenu4);

        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/customer.png"))); // NOI18N
        jMenuItem10.setText("Firma - Müşteri");
        jMenuItem10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem10);

        jMenuItem12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/project.png"))); // NOI18N
        jMenuItem12.setText("Proje");
        jMenuItem12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem12);

        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/surface.png"))); // NOI18N
        jMenuItem13.setText("Yuzay Durumu");
        jMenuItem13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem13);

        jMenuItem14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/stage.png"))); // NOI18N
        jMenuItem14.setText("Muayene Aşaması");
        jMenuItem14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem14);

        jMenuBar1.add(jMenu3);

        jMenu7.setText("Düzelt");
        jMenu7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jMenuItem16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/person2.png"))); // NOI18N
        jMenuItem16.setText("Personel");
        jMenuItem16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem16);

        jMenuItem25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/person.png"))); // NOI18N
        jMenuItem25.setText("Personel Yönetici");
        jMenuItem25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem25);

        jMenuItem17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/machine.png"))); // NOI18N
        jMenuItem17.setText("Ekipman");
        jMenuItem17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem17);

        jMenuItem18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/customer.png"))); // NOI18N
        jMenuItem18.setText("Firma - Müşteri");
        jMenuItem18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem18MouseClicked(evt);
            }
        });
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem18);

        jMenuItem20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/project.png"))); // NOI18N
        jMenuItem20.setText("Proje");
        jMenuItem20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem20);

        jMenuItem21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/surface.png"))); // NOI18N
        jMenuItem21.setText("Yuzay Durumu");
        jMenuItem21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem21);

        jMenuItem22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/berichtserstellungssystem/Images/stage.png"))); // NOI18N
        jMenuItem22.setText("Muayene Aşaması");
        jMenuItem22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem22);

        jMenuBar1.add(jMenu7);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleDescription("");

        setSize(new java.awt.Dimension(800, 500));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private int id = 0;
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        new FirstPage(true).setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        System.exit(1);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        new Login(this).setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        this.setState(this.ICONIFIED);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
        this.setMinimumSize(this.getMinimumSize());
        jMenuItem6.setVisible(false);
        jMenuItem7.setVisible(false);
        jMenuItem15.setVisible(false);
        jMenu3.setVisible(false);
        jMenu7.setVisible(false);
        jScrollPane2.setVisible(false);
        
    }//GEN-LAST:event_formWindowOpened

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        new CustomerFrame(1, "", me).setVisible(true);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        new Personel(DatabaseManagement.getMANAGER_STATUS(), 1, me).setVisible(true);
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        jScrollPane2.setVisible(true);
        if (jTabbedPane1.getComponentCount() < 3) {
            jTabbedPane1.addTab("Eklediklerim", jPanel2);
            jTabbedPane1.addTab("Düzelttiklerim", jPanel4);
        }
        jLabel1.setText("Müşteriler");
        prepareTables(7);
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        me = new Manager();
        jScrollPane2.setVisible(false);
        jLabel1.setText("");
        isManager(false);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuBar1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuBar1MouseDragged
        this.setLocation(evt.getXOnScreen()-(jMenuBar1.getWidth()/2), evt.getYOnScreen());
    }//GEN-LAST:event_jMenuBar1MouseDragged

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        new Personel(DatabaseManagement.getEMPLOYEE_STATUS(), 1, me).setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        new Personel(0, 2, me).setVisible(true);
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        jScrollPane2.setVisible(true);
        if (jTabbedPane1.getComponentCount() < 3) {
            jTabbedPane1.addTab("Eklediklerim", jPanel2);
            jTabbedPane1.addTab("Düzelttiklerim", jPanel4);
        }
        jLabel1.setText("Personel");
        prepareTables(1);
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        int row = jTable2.getSelectedRow();
        if (jLabel1.getText().equals("Personel")) {
            String personalNr = jTable2.getValueAt(row, 2).toString();
            new Personel(DatabaseManagement.getEMPLOYEE_STATUS(), 2, Long.parseLong(personalNr), me).setVisible(true);
        }
        else if (jLabel1.getText().equals("Personel Yönetici")) {
            String personalNr = jTable2.getValueAt(row, 2).toString();
            new Personel(DatabaseManagement.getMANAGER_STATUS(), 2, Long.parseLong(personalNr), me).setVisible(true);
        }
        else if (jLabel1.getText().equals("Ekipmanlar")) {
            String name = jTable2.getValueAt(row, 0).toString();
            System.out.println(name);
            int type;
            if (jTable2.getValueAt(row, 1).equals("Manyetik")) {
                type = DatabaseManagement.getMAGNETIC_TYPE();
            }
            else {
                type = DatabaseManagement.getRADIOGRAPHIC_TYPE();
            }
            System.out.println(type);
            new Ekipman(type, 2, name, me).setVisible(true);
        }
        else if (jLabel1.getText().equals("Projeler")) {
            String proje = jTable2.getValueAt(row, 0).toString();
            new Proje(1, 2, proje, me).setVisible(true);
        }
        else if (jLabel1.getText().equals("Yüzay Durumu")) {
            String yuzay = jTable2.getValueAt(row, 0).toString();
            new Proje(2, 2, yuzay, me).setVisible(true);
        }
        else if (jLabel1.getText().equals("Muayene Aşamaları")) {
            String muayene = jTable2.getValueAt(row, 0).toString();
            new Proje(3, 2, muayene, me).setVisible(true);
        }
        else if (jLabel1.getText().equals("Müşteriler")) {
            String name = jTable2.getValueAt(row, 0).toString();
            new CustomerFrame(2, name, me).setVisible(true);
        }
        
    }//GEN-LAST:event_jTable2MouseClicked

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        new Ekipman(DatabaseManagement.getMAGNETIC_TYPE(), 1, "", me).setVisible(true);
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
        new Ekipman(DatabaseManagement.getRADIOGRAPHIC_TYPE(), 1, "", me).setVisible(true);
    }//GEN-LAST:event_jMenuItem24ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        jScrollPane2.setVisible(true);
        if (jTabbedPane1.getComponentCount() < 3) {
            jTabbedPane1.addTab("Eklediklerim", jPanel2);
            jTabbedPane1.addTab("Düzelttiklerim", jPanel4);
        }
        jLabel1.setText("Ekipmanlar");
        prepareTables(3);
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        int row = jTable3.getSelectedRow();
        if (jLabel1.getText().equals("Personel")) {
            String personalNr = jTable3.getValueAt(row, 2).toString();
            new Personel(DatabaseManagement.getEMPLOYEE_STATUS(), 2, Long.parseLong(personalNr), me).setVisible(true);
        }
        else if (jLabel1.getText().equals("Personel Yönetici")) {
            String personalNr = jTable3.getValueAt(row, 2).toString();
            new Personel(DatabaseManagement.getMANAGER_STATUS(), 2, Long.parseLong(personalNr), me).setVisible(true);
        }
        else if (jLabel1.getText().equals("Ekipmanlar")) {
            String name = jTable3.getValueAt(row, 0).toString();
            System.out.println(name);
            int type;
            if (jTable3.getValueAt(row, 1).equals("Manyetik")) {
                type = DatabaseManagement.getMAGNETIC_TYPE();
            }
            else {
                type = DatabaseManagement.getRADIOGRAPHIC_TYPE();
            }
            System.out.println(type);
            new Ekipman(type, 2, name, me).setVisible(true);
        }
        else if (jLabel1.getText().equals("Projeler")) {
            String proje = jTable3.getValueAt(row, 0).toString();
            new Proje(1, 2, proje, me).setVisible(true);
        }
        else if (jLabel1.getText().equals("Yüzay Durumu")) {
            String yuzay = jTable3.getValueAt(row, 0).toString();
            new Proje(2, 2, yuzay, me).setVisible(true);
        }
        else if (jLabel1.getText().equals("Muayene Aşamaları")) {
            String muayene = jTable3.getValueAt(row, 0).toString();
            new Proje(3, 2, muayene, me).setVisible(true);
        }
    }//GEN-LAST:event_jTable3MouseClicked

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        new Proje(1, 1, "", me).setVisible(true);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        new Proje(2, 1, "", me).setVisible(true);
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        new Proje(3, 1, "", me).setVisible(true);
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed
        jScrollPane2.setVisible(true);
        while (jTabbedPane1.getComponentCount() != 1) {
           jTabbedPane1.removeTabAt(1);
        }
        jLabel1.setText("Personel Yönetici");
        prepareTables(2);
    }//GEN-LAST:event_jMenuItem25ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        jScrollPane2.setVisible(true);
        while (jTabbedPane1.getComponentCount() != 1) {
           jTabbedPane1.removeTabAt(1);
        }
        jLabel1.setText("Projeler");
        prepareTables(4);
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        jScrollPane2.setVisible(true);
        while (jTabbedPane1.getComponentCount() != 1) {
           jTabbedPane1.removeTabAt(1);
        }
        jLabel1.setText("Yüzay Durumu");
        prepareTables(5);
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        jScrollPane2.setVisible(true);
        while (jTabbedPane1.getComponentCount() != 1) {
           jTabbedPane1.removeTabAt(1);
        }
        jLabel1.setText("Muayene Aşamaları");
        prepareTables(6);
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        int row = jTable4.getSelectedRow();
        if (jLabel1.getText().equals("Personel")) {
            String personalNr = jTable4.getValueAt(row, 2).toString();
            new Personel(DatabaseManagement.getEMPLOYEE_STATUS(), 2, Long.parseLong(personalNr), me).setVisible(true);
        }
        else if (jLabel1.getText().equals("Personel Yönetici")) {
            String personalNr = jTable4.getValueAt(row, 2).toString();
            new Personel(DatabaseManagement.getMANAGER_STATUS(), 2, Long.parseLong(personalNr), me).setVisible(true);
        }
        else if (jLabel1.getText().equals("Ekipmanlar")) {
            String name = jTable4.getValueAt(row, 0).toString();
            System.out.println(name);
            int type;
            if (jTable4.getValueAt(row, 1).equals("Manyetik")) {
                type = DatabaseManagement.getMAGNETIC_TYPE();
            }
            else {
                type = DatabaseManagement.getRADIOGRAPHIC_TYPE();
            }
            System.out.println(type);
            new Ekipman(type, 2, name, me).setVisible(true);
        }
        else if (jLabel1.getText().equals("Projeler")) {
            String proje = jTable4.getValueAt(row, 0).toString();
            new Proje(1, 2, proje, me).setVisible(true);
        }
        else if (jLabel1.getText().equals("Yüzay Durumu")) {
            String yuzay = jTable4.getValueAt(row, 0).toString();
            new Proje(2, 2, yuzay, me).setVisible(true);
        }
        else if (jLabel1.getText().equals("Muayene Aşamaları")) {
            String muayene = jTable4.getValueAt(row, 0).toString();
            new Proje(3, 2, muayene, me).setVisible(true);
        }
    }//GEN-LAST:event_jTable4MouseClicked

    private void jMenuItem18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem18MouseClicked
        
    }//GEN-LAST:event_jMenuItem18MouseClicked
    
    private void showPanel (String s){
        this.setMinimumSize(this.getMinimumSize());
    }
    
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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    // End of variables declaration//GEN-END:variables
}
