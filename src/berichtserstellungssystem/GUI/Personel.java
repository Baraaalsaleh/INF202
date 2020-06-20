//Baraa Alsaleh, 19050800
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package berichtserstellungssystem.GUI;

import berichtserstellungssystem.Common;
import berichtserstellungssystem.DatabaseManagement.*;
import berichtserstellungssystem.Resource.*;
import berichtserstellungssystem.Security;
import berichtserstellungssystem.Verification;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author Baraa
 */
public class Personel extends javax.swing.JFrame {

    int type;
    int process;
    long personalNr;
    long tc;
    private Manager me;
    String name = "";
    String lastname = "";
    String gender = "";
    String telephone = "";
    String address = "";
    String email = "";
    Date birthdate = null;
    String TCNr = "";
    String Pnr = "";
    String var1 = "";
    String var2 = "";
    Date var3 = null;
    boolean verifiy;
    
    Menu frame = null;
    
    boolean testIt1 = false;
    boolean testIt2 = false;
    
    
    /**
     * Creates new form Personel
     */
    public Personel() {
        initComponents();
    }
    
    public Personel(int type, int process, long personalNr, Manager me) {
        initComponents();
        this.type = type;
        this.process = process;
        this.me = me;
        this.personalNr = personalNr;
        setEveryThing();
    }
    
    public Personel(int type, int process, Manager me, Menu frame) {
        initComponents();
        this.type = type;
        this.process = process;
        this.me = me;
        this.frame = frame;
        setEveryThing();
    }
    
    private void fillData (Person toEdit) {
        jTextField1.setText(toEdit.getName());
        jTextField2.setText(toEdit.getLastname());
        jTextField4.setText(Long.toString(toEdit.getTelephone()));
        int gender = 1;
        if (toEdit.getGender().trim().equals("Erkek")) {
            gender = 0;
        }
        jComboBox1.setSelectedIndex(gender);
        jTextField5.setText(toEdit.getEmail());
        jTextField6.setText(toEdit.getAddress());
        jDateChooser1.setDate(toEdit.getBirthDate());
        jTextField8.setText(Long.toString(toEdit.getTcNr()));
        this.tc = toEdit.getTcNr();
        jTextField9.setText(Long.toString(toEdit.getPersonalNr()));
    }
    
    private void fillEmployeeData (Employee toEdit) {
        fillData(toEdit);
        jDateChooser2.setDate(toEdit.getPermitionEndDate());
        jTextField11.setText(Integer.toString(toEdit.getLevel()));
    }
    
    private void setEveryThing() {
        if (process == 1) {
            jButton3.setVisible(false);
            if (type == DatabaseManagement.getEMPLOYEE_STATUS()){
                this.jLabel1.setText("Personel Ekle");
                this.jTextField10.setVisible(false);
            }
            else if (type == DatabaseManagement.getMANAGER_STATUS()){
                this.jLabel1.setText("Yönetici Ekle");
                this.jLabel11.setVisible(false);
                this.jTextField10.setVisible(false);
                this.jDateChooser2.setVisible(false);
                this.jLabel12.setVisible(false);
                this.jTextField11.setVisible(false);
            }
            else {
                this.jLabel1.setText("Admin Ekle");
                this.jLabel11.setVisible(false);
                this.jTextField10.setVisible(false);
                this.jDateChooser2.setVisible(false);
                this.jLabel12.setVisible(false);
                this.jTextField11.setVisible(false);
            }
        }
        else {
            jButton1.setText("Güncelle");
            jTextField1.setEnabled(false);
            jTextField2.setEnabled(false);
            jTextField9.setEnabled(false);
            if (type == DatabaseManagement.getEMPLOYEE_STATUS()){
                    this.jLabel1.setText("Personel Güncelle");
                    ResultSet rs = PersonManagement.getEmployee(personalNr);
                    Employee toEdit = new Employee(rs);
                    System.out.println("this is me " + toEdit.getName());
                    fillEmployeeData(toEdit);
                    jTextField10.setVisible(false);
            }
            else if (type == DatabaseManagement.getMANAGER_STATUS()){
                this.jLabel1.setText("Yönetici Güncelle");
                this.jLabel11.setVisible(false);
                this.jTextField10.setVisible(false);
                this.jDateChooser2.setVisible(false);
                this.jLabel12.setVisible(false);
                this.jTextField11.setVisible(false);
                ResultSet rs = PersonManagement.getManager(personalNr);
                Manager toEdit = new Manager(rs); 
                fillData(toEdit);
            }
            else {
                jButton3.setVisible(false);
                fillData(me);
                this.jLabel1.setText("Benim Bilgilerim");
                this.jLabel11.setText("Kullanıcı Adı");
                this.jTextField10.setText("Kullanıcı Adı");
                this.jTextField10.setBackground(Color.pink);
                this.jTextField10.setToolTipText("Zorunlu alan");
                this.jDateChooser2.setVisible(false);
                this.jLabel12.setText("Şifre");
                this.jTextField11.setText("Şifre");
                this.jTextField11.setBackground(Color.pink);
                this.jTextField11.setToolTipText("Zorunlu alan");
                jTextField8.setEnabled(false);
                jDateChooser1.setEnabled(false);
                jComboBox1.setEnabled(false);
            }
        }
    }
    
    private boolean everyThingIsOkay () {
        if (Verification.verifyName(jTextField1.getText().trim()) && Verification.verifyName(jTextField2.getText().trim()) && Verification.verifyTelephoneNumber(jTextField4.getText().trim())
                && Verification.verifyEmail(jTextField5.getText().trim())  && jDateChooser1.getDate() != null
                && Verification.verifyTCnumber(jTextField8.getText().trim()) && Verification.isNumber(jTextField9.getText().trim())){
            if (type == DatabaseManagement.getEMPLOYEE_STATUS()) {
                if (jDateChooser2.getDate() != null && Verification.isNumber(jTextField11.getText().trim())) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                if (process == 2 && type == 0) {
                    if (Verification.verifyUsername(jTextField10.getText().trim()) && PersonManagement.userNameAccepted(jTextField10.getText().trim()) && Verification.verifyPassword(jTextField11.getText().trim())) {
                        return true;
                    }
                    else if (Verification.verifyUsername(jTextField10.getText().trim()) && !PersonManagement.userNameAccepted(jTextField10.getText().trim()) && me.getUsername().equals(jTextField10.getText().trim()) && Verification.verifyPassword(jTextField11.getText().trim())) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                else {
                    return true;
                }
            }
        }
        else {
            return false;
        }
    }
    
    private void everyThingIsOkay (int[] index) {
        for (Integer i : index){
            switch (i){
                case 1:
                    if (!Verification.verifyName(jTextField1.getText().trim())) {
                        jTextField1.setBackground(Color.pink);
                        jTextField1.setToolTipText("Geçerli bir ad giriniz!");
                    }
                    else {
                        if (jTextField1.getText().trim().equals("Adı")) {
                            jTextField1.setBackground(Color.pink);
                            jTextField1.setToolTipText("Adı girmeniz zorunludur!");
                        }
                        else {
                            jTextField1.setBackground(Color.white);
                            jTextField1.setToolTipText(null);
                        }
                    }
                    break;
                case 2:
                    if (!Verification.verifyName(jTextField2.getText().trim())) {
                        jTextField2.setBackground(Color.pink);
                        jTextField2.setToolTipText("Geçerli bir ad giriniz!");
                    }
                    else {
                        if (jTextField2.getText().trim().equals("Soyadı")) {
                            jTextField2.setBackground(Color.pink);
                            jTextField2.setToolTipText("Soyadı girmeniz zorunludur!");
                        }
                        else {
                            jTextField2.setBackground(Color.white);
                            jTextField2.setToolTipText(null);
                        }
                    }
                    break;
                case 4:
                    if (!Verification.verifyTelephoneNumber(jTextField4.getText().trim())) {
                        jTextField4.setBackground(Color.pink);
                        jTextField4.setToolTipText("Geçerli bir telefon nummarasi giriniz!");
                    }
                    else {
                        jTextField4.setBackground(Color.white);
                        jTextField4.setToolTipText(null);
                    }
                    break;
                case 5:
                    if (!Verification.verifyEmail(jTextField5.getText().trim())) {
                        jTextField5.setBackground(Color.pink);
                        jTextField5.setToolTipText("Geçerli bir E-Posta adresi giriniz!");
                    }
                    else {
                        jTextField5.setBackground(Color.white);
                        jTextField5.setToolTipText(null);
                    }
                    break;
                case 7:
                    if (jDateChooser1.getDate() == null) {
                        jDateChooser1.setBackground(Color.pink);
                        jDateChooser1.setToolTipText("Zorunlu alan! Geçerli bir tarih seçiniz!");
                    }
                    else {
                        jDateChooser1.setBackground(Color.white);
                        jDateChooser1.setToolTipText(null);
                    }
                    break;
                case 8:
                    if (!Verification.verifyTCnumber(jTextField8.getText().trim())) {
                        jTextField8.setBackground(Color.pink);
                        jTextField8.setToolTipText("Geçerli bir TC-Numara giriniz! TC-Numarası 11 Haneden oluşur");
                    }
                    else {
                        if (process == 1) {
                            if (PersonManagement.tcummerAccepted(Long.parseLong(jTextField8.getText().trim()))) {
                                jTextField8.setBackground(Color.white);
                                jTextField8.setToolTipText(null);
                            }
                            else {
                                jTextField8.setBackground(Color.pink);
                                jTextField8.setToolTipText("Girdiğiniz TC-Numarası veri tabanında bulunduğu için tekrar kullanılmaz");
                            }
                        }
                        else {
                            if (PersonManagement.tcummerAccepted(Long.parseLong(jTextField8.getText().trim())) || Long.toString(this.tc).equals(jTextField8.getText().trim())) {
                                jTextField8.setBackground(Color.white);
                                jTextField8.setToolTipText(null);
                            }
                            else {
                                jTextField8.setBackground(Color.pink);
                                jTextField8.setToolTipText("Girdiğiniz TC-Numarası veri tabanında bulunduğu için tekrar kullanılmaz");
                            }
                        }
                        
                    }
                    break;
                case 9:
                    if (!Verification.isNumber(jTextField9.getText().trim())) {
                        jTextField9.setBackground(Color.pink);
                        jTextField9.setToolTipText("Geçerli bir personal nummara giriniz! Personal nummara sadece sayılardan oluşmalı!");
                    }
                    else {
                        if (PersonManagement.personalNummerAccepted(Long.parseLong(jTextField9.getText().trim()))) {
                            jTextField9.setBackground(Color.white);
                            jTextField9.setToolTipText(null);
                        }
                        else {
                            jTextField9.setBackground(Color.pink);
                            jTextField9.setToolTipText("Girdiğiniz personal nummara veri tabanında bulunduğu için tekrar kullanılmaz");
                        }
                    }
                    break;
                case 10:
                    if (type == DatabaseManagement.getEMPLOYEE_STATUS()) {
                        if (jDateChooser2.getDate() == null) {
                            jDateChooser2.setBackground(Color.pink);
                            jDateChooser2.setToolTipText("Zorunlu alan! Geçerli bir tarih seçiniz!");
                        }
                        else {
                            jDateChooser2.setBackground(Color.white);
                            jDateChooser2.setToolTipText(null);
                        }
                    }
                    else {
                        if (process == 2 && type == 0) {
                            if (!Verification.verifyUsername(jTextField10.getText().trim())) {
                                jTextField10.setBackground(Color.pink);
                                jTextField10.setToolTipText("Geçerli bir kullanıcı adı giriniz! Kullanıcı adı sadece inglizce harfler ve sayılar içirebilir (boşluk olmaksızın)!");
                            }
                            else if (!PersonManagement.userNameAccepted(jTextField10.getText().trim()) && !me.getUsername().equals(jTextField10.getText().trim())) {
                                jTextField10.setBackground(Color.pink);
                                jTextField10.setToolTipText("Girdiğiniz kullanıcı adı başka bir kullanıcı tarıfından kullanılmaktadır! Farklı bir kullanıcı adı giriniz!");
                            }
                            else {
                                jTextField10.setBackground(Color.white);
                                jTextField10.setToolTipText(null);
                            }
                        }
                        else {
                        }
                    }
                    break;
                case 11:
                    if (type == DatabaseManagement.getEMPLOYEE_STATUS()) {
                        if (!Verification.isNumber(jTextField11.getText().trim())) {
                            jTextField11.setBackground(Color.pink);
                            jTextField11.setToolTipText("Geçerli bir seviye giriniz! Seviye sadece 1 - 5 arasında bir sayı olabilir!");
                        }
                        else {
                            if (Integer.parseInt(jTextField11.getText().trim()) < 6 && Integer.parseInt(jTextField11.getText().trim()) > 0) {
                                jTextField11.setBackground(Color.white);
                                jTextField11.setToolTipText(null);
                            }
                            else {
                                jTextField11.setBackground(Color.pink);
                                jTextField11.setToolTipText("Geçerli bir seviye giriniz! Seviye sadece 1 - 5 arasında bir sayı olabilir!");
                            }
                        }
                    }
                    else {
                        if (process == 2 && type == 0) {
                            if (!Verification.verifyPassword(jTextField11.getText().trim())) {
                                jTextField11.setBackground(Color.pink);
                                jTextField10.setToolTipText("Geçerli bir şifre giriniz! Şifre sadece inglizce harfler, sayılar ve simbollar içirebilir (boşluk olmaksızın)! Uzunluğu ise 8 - 32 olabilir!");
                            }
                            else {
                                jTextField11.setBackground(Color.white);
                                jTextField11.setToolTipText(null);
                            }
                        }
                        else {
                        }
                    }
                    break;
            }
        }
    }
    
    private void cleanAll () {
        testIt1 = false;
        testIt2 = false;
        jTextField1.setText("Adı");
        jTextField2.setText("Soyadı");
        jTextField4.setText("Telefon");
        jTextField5.setText("E-Posta");
        jTextField6.setText("Adres");
        jDateChooser1.setDate(null);
        jTextField8.setText("TCNr");
        jTextField9.setText("Personel-Nr");
        if (type == DatabaseManagement.getEMPLOYEE_STATUS()) {
            jDateChooser2.setDate(null);
            jTextField11.setText("Seviye");
        }
        else {
            jTextField10.setText("Kullanıcı Adı");
            jTextField11.setText("Şifre");
        }
        jButton1.setEnabled(false);
        testIt1 = true;
        testIt2 = true;
    }
    
    private void getData () {
        name = Common.makeFirstLetterCapital(jTextField1.getText().trim());
        lastname = jTextField2.getText().toUpperCase().trim();
        gender = jComboBox1.getSelectedItem().toString();
        telephone = jTextField4.getText().trim();
        address = jTextField6.getText().trim();
        email = jTextField5.getText().trim();
        birthdate = jDateChooser1.getDate();
        TCNr = jTextField8.getText().trim();
        Pnr = jTextField9.getText().trim();
        if (type == DatabaseManagement.getEMPLOYEE_STATUS()) {
            var3 = jDateChooser2.getDate();
        }
        else {
            var1 = jTextField10.getText().trim();
        }
        var2 = jTextField11.getText().trim();
    }
    
    private Employee employeeDataCollector () {
        getData();
        Employee temp = new Employee();
        temp.setName(name);
        temp.setLastname(lastname);
        temp.setGender(gender);
        temp.setTelephone(Long.parseLong(telephone));
        temp.setAddress(address);
        temp.setEmail(email);
        temp.setBirthDate(birthdate);
        temp.setTcNr(Long.parseLong(TCNr));
        temp.setPersonalNr(Long.parseLong(Pnr));
        temp.setPermitionEndDate(var3);
        temp.setLevel(Integer.parseInt(var2));
        return temp;
    }
    
    private Manager managerDataCollector () {
        getData();
        Manager temp = new Manager();
        temp.setName(name);
        temp.setLastname(lastname);
        temp.setGender(gender);
        temp.setTelephone(Long.parseLong(telephone));
        temp.setAddress(address);
        temp.setEmail(email);
        temp.setBirthDate(birthdate);
        temp.setTcNr(Long.parseLong(TCNr));
        temp.setPersonalNr(Long.parseLong(Pnr));
        if (type == 0) {
            temp.setUsername(var1);
            temp.setPassword(Security.generateHash(var2));
        }
        return temp;
    }
 
    private void message (int done) {
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
            else if (type == 0){
                frame.me = new Manager(PersonManagement.getManager(Long.parseLong(jTextField9.getText())));
                this.dispose();
            }
        }
        else if (done == 0) {
            JOptionPane.showMessageDialog(dialog, "Girdiğiniz personel numarası daha önce veri tabanında bulunduğu için kullanılmaz!", "Hatalı İşlem", JOptionPane.PLAIN_MESSAGE);
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
    
    private void add () {
        if (type == DatabaseManagement.getEMPLOYEE_STATUS()) {
            Employee toAdd = employeeDataCollector();
            int done = PersonManagement.insertEmployee(toAdd, me);      
            message(done);
        }
        else if (type == DatabaseManagement.getMANAGER_STATUS()){
            Manager toAdd = managerDataCollector();
            int done = PersonManagement.insertManager(toAdd, DatabaseManagement.getMANAGER_STATUS());
            message(done);
        }
        else {
            Manager toAdd = managerDataCollector();
            int done = PersonManagement.insertManager(toAdd, DatabaseManagement.getAdmin_STATUS());
            message(done);
            this.dispose();
        }
    }
    
    private void update() {
        if (type == DatabaseManagement.getEMPLOYEE_STATUS()) {
            Employee toUpdate = employeeDataCollector();
            System.out.println(toUpdate.getName() + " " + toUpdate.getLastname());
            int done = PersonManagement.updateEmployee(toUpdate, me);      
            message(done);
        }
        else if (type == DatabaseManagement.getMANAGER_STATUS()){
            Manager toUpdate = managerDataCollector();
            int done = PersonManagement.updateManager(toUpdate);
            message(done);
        }
        else {
            
            Manager toUpdate = managerDataCollector();
            int done = PersonManagement.updateManager_self(toUpdate);
            System.out.println("It was successfully done, and as a profe, your name is " + toUpdate.getName() + " " + toUpdate.getLastname() + " " + toUpdate.getPersonalNr()
            + " and you were born on the " + toUpdate.getBirthDate() + " and this is another test " + Common.date_toString(toUpdate.getBirthDate()) + toUpdate.getUsername()
            + "    " +toUpdate.getPassword());
            message(done);
        }
    }
    
    void delete() {
        long PNR = Long.parseLong(jTextField9.getText());
        process = 3;
        message(PersonManagement.deletePerson(PNR));
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
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setMaximumSize(new java.awt.Dimension(900, 600));
        setMinimumSize(new java.awt.Dimension(900, 600));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));
        jPanel1.setLayout(null);

        jLabel1.setBackground(new java.awt.Color(0, 51, 51));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Title");
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jLabel1.setOpaque(true);
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 937, 68);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("Adı:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(10, 98, 201, 42);

        jTextField1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTextField1.setText("Adı");
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel1.add(jTextField1);
        jTextField1.setBounds(229, 100, 191, 42);

        jTextField2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTextField2.setText("Soyadı");
        jTextField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField2FocusGained(evt);
            }
        });
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });
        jPanel1.add(jTextField2);
        jTextField2.setBounds(657, 100, 191, 42);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("Soyadı:");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(438, 98, 201, 42);

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("Cinsiyet:");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(10, 160, 201, 42);

        jTextField4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTextField4.setText("Telefon");
        jTextField4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField4FocusGained(evt);
            }
        });
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });
        jPanel1.add(jTextField4);
        jTextField4.setBounds(657, 160, 191, 42);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel5.setText("Telefon");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(438, 160, 201, 42);

        jTextField5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTextField5.setText("E-Posta");
        jTextField5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField5FocusGained(evt);
            }
        });
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField5KeyReleased(evt);
            }
        });
        jPanel1.add(jTextField5);
        jTextField5.setBounds(229, 222, 619, 42);

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("E-Posta");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(10, 220, 201, 42);

        jTextField6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTextField6.setText("Adres");
        jTextField6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField6FocusGained(evt);
            }
        });
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField6KeyReleased(evt);
            }
        });
        jPanel1.add(jTextField6);
        jTextField6.setBounds(229, 284, 619, 42);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setText("Adres");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(10, 282, 201, 42);

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel8.setText("Doğum tarihi");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(10, 344, 201, 42);

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel9.setText("TC - Numarası:");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(438, 344, 201, 42);

        jTextField8.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTextField8.setText("TCNr");
        jTextField8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField8FocusGained(evt);
            }
        });
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });
        jTextField8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField8KeyReleased(evt);
            }
        });
        jPanel1.add(jTextField8);
        jTextField8.setBounds(657, 346, 191, 42);

        jTextField9.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTextField9.setText("Personel-Nr");
        jTextField9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField9FocusGained(evt);
            }
        });
        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });
        jTextField9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField9KeyReleased(evt);
            }
        });
        jPanel1.add(jTextField9);
        jTextField9.setBounds(229, 408, 191, 42);

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel10.setText("Personel - Nr");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(10, 406, 201, 42);

        jComboBox1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Erkek", "Kadın" }));
        jPanel1.add(jComboBox1);
        jComboBox1.setBounds(229, 160, 191, 42);

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel11.setText("İzin Bitme tarihi");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(438, 406, 201, 42);

        jTextField10.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTextField10.setText("Kullanıcı Adı");
        jTextField10.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField10FocusGained(evt);
            }
        });
        jTextField10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField10ActionPerformed(evt);
            }
        });
        jTextField10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField10KeyReleased(evt);
            }
        });
        jPanel1.add(jTextField10);
        jTextField10.setBounds(657, 408, 191, 42);

        jTextField11.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTextField11.setText("Seviye");
        jTextField11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField11FocusGained(evt);
            }
        });
        jTextField11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField11ActionPerformed(evt);
            }
        });
        jTextField11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField11KeyReleased(evt);
            }
        });
        jPanel1.add(jTextField11);
        jTextField11.setBounds(448, 470, 191, 42);

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel12.setText("Seviye");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(229, 468, 191, 42);

        jButton1.setBackground(new java.awt.Color(0, 153, 102));
        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Ekle");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(694, 523, 191, 44);

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton2.setText("İptal Et");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(20, 523, 191, 44);

        jButton3.setBackground(new java.awt.Color(255, 51, 51));
        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton3.setText("Sil");
        jButton3.setToolTipText("");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(356, 523, 191, 44);

        jDateChooser2.setDateFormatString("dd - MM - yyyy");
        jDateChooser2.setMaxSelectableDate(new java.util.Date(2524604509000L));
        jDateChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser2PropertyChange(evt);
            }
        });
        jPanel1.add(jDateChooser2);
        jDateChooser2.setBounds(657, 408, 191, 44);

        jDateChooser1.setDateFormatString("dd - MM - yyyy");
        jDateChooser1.setMaxSelectableDate(new java.util.Date(253370761266000L));
        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });
        jPanel1.add(jDateChooser1);
        jDateChooser1.setBounds(229, 344, 191, 44);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 920, 590);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField9ActionPerformed

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField11ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField11ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (type == 0 && process == 1) {
            this.setDefaultCloseOperation(1);
        }
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusGained
            jTextField1.selectAll();
    }//GEN-LAST:event_jTextField1FocusGained

    private void jTextField2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusGained
            jTextField2.selectAll();
    }//GEN-LAST:event_jTextField2FocusGained

    private void jTextField4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField4FocusGained
            jTextField4.selectAll();
    }//GEN-LAST:event_jTextField4FocusGained

    private void jTextField5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField5FocusGained
            jTextField5.selectAll();
    }//GEN-LAST:event_jTextField5FocusGained

    private void jTextField6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField6FocusGained
        jTextField6.selectAll();
    }//GEN-LAST:event_jTextField6FocusGained

    private void jTextField8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField8FocusGained
        jTextField8.selectAll();
    }//GEN-LAST:event_jTextField8FocusGained

    private void jTextField9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField9FocusGained
        jTextField9.selectAll();
    }//GEN-LAST:event_jTextField9FocusGained

    private void jTextField11FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField11FocusGained
        jTextField11.selectAll();
    }//GEN-LAST:event_jTextField11FocusGained

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        if (everyThingIsOkay()) {
            jButton1.setEnabled(true);
        }
        else {
            jButton1.setEnabled(false);
        }
        int[] toCheck = {1};
        everyThingIsOkay(toCheck);
    }//GEN-LAST:event_jTextField1KeyReleased

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased

    }//GEN-LAST:event_formKeyReleased

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        if (everyThingIsOkay()) {
            jButton1.setEnabled(true);
        }
        else {
            jButton1.setEnabled(false);
        }
        int[] toCheck = {1, 2};
        everyThingIsOkay(toCheck);
    }//GEN-LAST:event_jTextField2KeyReleased

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        if (everyThingIsOkay()) {
            jButton1.setEnabled(true);
        }
        else {
            jButton1.setEnabled(false);
        }
        int[] toCheck = {1, 2, 4};
        everyThingIsOkay(toCheck);
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jTextField5KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyReleased
        if (everyThingIsOkay()) {
            jButton1.setEnabled(true);
        }
        else {
            jButton1.setEnabled(false);
        }
        int[] toCheck = {1, 2, 4, 5};
        everyThingIsOkay(toCheck);
    }//GEN-LAST:event_jTextField5KeyReleased

    private void jTextField6KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyReleased
        if (everyThingIsOkay()) {
            jButton1.setEnabled(true);
        }
        else {
            jButton1.setEnabled(false);
        }
        int[] toCheck = {1, 2, 4, 5, 6};
        everyThingIsOkay(toCheck);
    }//GEN-LAST:event_jTextField6KeyReleased

    private void jTextField8KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField8KeyReleased
        if (everyThingIsOkay()) {
            jButton1.setEnabled(true);
        }
        else {
            jButton1.setEnabled(false);
        }
        int[] toCheck = {1, 2, 4, 5, 6, 7, 8};
        everyThingIsOkay(toCheck);
    }//GEN-LAST:event_jTextField8KeyReleased

    private void jTextField9KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField9KeyReleased
        if (everyThingIsOkay()) {
            jButton1.setEnabled(true);
        }
        else {
            jButton1.setEnabled(false);
        }
        int[] toCheck = {1, 2, 4, 5, 6, 7, 8, 9};
        everyThingIsOkay(toCheck);
    }//GEN-LAST:event_jTextField9KeyReleased

    private void jTextField11KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField11KeyReleased
        if (everyThingIsOkay()) {
            jButton1.setEnabled(true);
        }
        else {
            jButton1.setEnabled(false);
        }
        int[] toCheck = {1, 2, 4, 5, 6, 7, 8, 9, 10, 11};
        everyThingIsOkay(toCheck);
    }//GEN-LAST:event_jTextField11KeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (jButton1.isEnabled()) {
            if (process == 1){
                add();
            }    
            else {
                update();
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Date temp = new Date();
        jDateChooser2.setMinSelectableDate(temp);
        temp = new Date();
        int year = temp.getYear();
        temp.setYear(year+30);
        jDateChooser2.setMaxSelectableDate(temp);
        temp = new Date();
        year = temp.getYear();
        temp.setYear(year-18);
        jDateChooser1.setMaxSelectableDate(temp);
        temp = new Date();
        year = temp.getYear();
        temp.setYear(year-80);
        jDateChooser1.setMinSelectableDate(temp);
        testIt1 = true;
        testIt2 = true;
    }//GEN-LAST:event_formWindowOpened

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        new Verify((jTextField1.getText() + " " + jTextField2.getText() + "'in bilgileri silmekten emin misiniz?"),this, 1).setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        
    }//GEN-LAST:event_jTextField1FocusLost

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField10KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField10KeyReleased
        if (everyThingIsOkay()) {
            jButton1.setEnabled(true);
        }
        else {
            jButton1.setEnabled(false);
        }
        int[] toCheck = {1, 2, 4, 5, 6, 7, 8, 9, 10};
        everyThingIsOkay(toCheck);
    }//GEN-LAST:event_jTextField10KeyReleased

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField10ActionPerformed

    private void jTextField10FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField10FocusGained
        jTextField10.selectAll();
    }//GEN-LAST:event_jTextField10FocusGained

    private void jDateChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser2PropertyChange
        if (testIt2) {
            if (everyThingIsOkay()) {
                jButton1.setEnabled(true);
            }
            else {
                jButton1.setEnabled(false);
            }
            int[] toCheck = {1, 2, 4, 5, 6, 7, 8, 9, 10};
            everyThingIsOkay(toCheck);
        }
    }//GEN-LAST:event_jDateChooser2PropertyChange

    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
        if (testIt1) {
            if (everyThingIsOkay()) {
                jButton1.setEnabled(true);
            }
            else {
                jButton1.setEnabled(false);
            }
            int[] toCheck = {1, 2, 4, 5, 6, 7};
            everyThingIsOkay(toCheck);
        }
        Date temp = jDateChooser1.getDate();
        if (temp != null && type == DatabaseManagement.getEMPLOYEE_STATUS()) {
            int year = temp.getYear();
            temp.setYear(year+18);
            testIt2 = false;
            jDateChooser2.setMinSelectableDate(temp);
            testIt2 = true;
        }
    }//GEN-LAST:event_jDateChooser1PropertyChange

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
            java.util.logging.Logger.getLogger(Personel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Personel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Personel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Personel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Personel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables


    




}



