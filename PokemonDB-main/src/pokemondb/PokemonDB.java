/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemondb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import pagination.EventPagination;
//import java.text.*;
//import java.util.Locale;

/**
 *
 * @author x7
 */
public class PokemonDB extends javax.swing.JFrame {

    /**
     * Creates new form PokemonDB
     */
    public PokemonDB() {
        initComponents();
        pagination1.addEventPagination(new EventPagination() {
            @Override
            public void pageChanged(int page) {
                updateTable(page);
            }
        });
        clear();
    }
    
//    private Connect conn = new Connect();
    
    public void updateTable(int page) {
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/pokemon","root","");  
            DefaultTableModel tblModel = (DefaultTableModel) dex_table.getModel();
            tblModel.setRowCount(0);
            int limit = 10;
            int count = 0;
            String sqlcount = "select count(*) from dex";
            PreparedStatement p = con.prepareStatement(sqlcount);
            ResultSet r = p.executeQuery();
            while (r.next()) {
                count = r.getInt(1);
            }
//            if (r.first()) {
//                count = r.getInt(1);
//            }
            r.close();
            p.close();
            int totalPage = (int) Math.ceil(count/limit);
            pagination1.setPagegination(page, totalPage);
            
            Statement st = con.createStatement();  
            ResultSet dex = st.executeQuery("select * from dex inner join stats on id = sid inner join info on id = iid limit " + (page - 1)*limit + ", " + limit);
            
            
            while(dex.next()) {
                String id = String.valueOf(dex.getInt("id"));
                String name = dex.getString("name");
                String type1 = dex.getString("type1");
                String type2 = dex.getString("type2");
                String hp = String.valueOf(dex.getInt("hp"));
                String atk = String.valueOf(dex.getInt("atk"));
                String def = String.valueOf(dex.getInt("def"));
                String spa = String.valueOf(dex.getInt("spa"));
                String spd  = String.valueOf(dex.getInt("spd"));
                String spe = String.valueOf(dex.getInt("spe"));
                String height = dex.getString("height");
                String weight = dex.getString("weight");
                String egg_group1 = dex.getString("egg_group1");
                String egg_group2 = dex.getString("egg_group2");
                
                String tbData[] = {id, name, type1, type2, hp, atk, def,spa, spd, spe,height, weight, egg_group1, egg_group2};
//                tblModel = (DefaultTableModel) dex_table.getModel();    
                
                tblModel.addRow(tbData);
            }
            con.close();  
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void clear() {
        this.id.setText("");
        this.name.setText("");
        this.type1.setSelectedIndex(0);
        this.type2.setSelectedIndex(0);
        this.hp.setText("");
        this.atk.setText("");
        this.def.setText("");
        this.spa.setText("");
        this.spd.setText("");
        this.spe.setText("");
        this.height.setText("");
        this.weight.setText("");
        this.egg_group1.setSelectedIndex(0);
        this.egg_group2.setSelectedIndex(0);
    }
    
    public void addDex() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokemon","root","");  

            String query = " insert into dex (name, type1, type2)" + " values (?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, this.name.getText());
            preparedStmt.setString (2, this.type1.getSelectedItem().toString());
            preparedStmt.setString (3, this.type2.getSelectedItem().toString());
            preparedStmt.execute();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void addStats() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokemon","root","");
            String query = " insert into stats (hp, atk, def, spa, spd, spe)" + " values (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt (1, Integer.parseInt(this.hp.getText()));
            preparedStmt.setInt (2, Integer.parseInt(this.atk.getText()));
            preparedStmt.setInt (3, Integer.parseInt(this.def.getText()));
            preparedStmt.setInt (4, Integer.parseInt(this.spa.getText()));
            preparedStmt.setInt (5, Integer.parseInt(this.spd.getText()));
            preparedStmt.setInt (6, Integer.parseInt(this.spe.getText()));
            preparedStmt.execute();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void addInfo() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokemon","root","");
            String query = " insert into info (height, weight, egg_group1, egg_group2)" + " values (?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, this.height.getText());
            preparedStmt.setString (2, this.weight.getText());
            preparedStmt.setString (3, this.egg_group1.getSelectedItem().toString());
            preparedStmt.setString (4, this.egg_group2.getSelectedItem().toString());
            preparedStmt.execute();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void updateDex() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokemon","root","");  

            String query = "update dex set name = ?, type1 = ?, type2 = ? where id = ?";
            PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
            preparedStmt.setString(1, this.name.getText());
            preparedStmt.setString(2, this.type1.getSelectedItem().toString());
            preparedStmt.setString(3, this.type2.getSelectedItem().toString());
            preparedStmt.setInt(4, Integer.parseInt(this.id.getText()));
            preparedStmt.executeUpdate();
            DefaultTableModel tmodel = (DefaultTableModel) dex_table.getModel();
            int selectrowindex = dex_table.getSelectedRow();
            id.setText(tmodel.getValueAt(selectrowindex, 0).toString());
            name.setText(tmodel.getValueAt(selectrowindex, 1).toString());
            type1.setSelectedItem(tmodel.getValueAt(selectrowindex, 2).toString());
            type2.setSelectedItem(tmodel.getValueAt(selectrowindex, 3).toString());
            //address.setText(tmodel.getValueAt(selectrowindex, 4).toString());
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void updateStats() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokemon","root","");  

            String query = "update stats set hp = ?, atk = ?, def = ?, spa = ?, spd = ?, spe = ? where sid = ?";
            PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
            preparedStmt.setInt(1, Integer.parseInt(this.hp.getText()));
            preparedStmt.setInt(2, Integer.parseInt(this.atk.getText()));
            preparedStmt.setInt(3, Integer.parseInt(this.def.getText()));
            preparedStmt.setInt(4, Integer.parseInt(this.spa.getText()));
            preparedStmt.setInt(5, Integer.parseInt(this.spd.getText()));
            preparedStmt.setInt(6, Integer.parseInt(this.spe.getText()));
            preparedStmt.setInt(7, Integer.parseInt(this.id.getText()));

            preparedStmt.executeUpdate();
            DefaultTableModel tmodel = (DefaultTableModel) dex_table.getModel();
            int selectrowindex = dex_table.getSelectedRow();
            hp.setText(tmodel.getValueAt(selectrowindex, 4).toString());
            atk.setText(tmodel.getValueAt(selectrowindex, 5).toString());
            def.setText(tmodel.getValueAt(selectrowindex, 6).toString());
            spa.setText(tmodel.getValueAt(selectrowindex, 7).toString());
            spd.setText(tmodel.getValueAt(selectrowindex, 8).toString());
            spe.setText(tmodel.getValueAt(selectrowindex, 9).toString());
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void updateInfo() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokemon","root","");  

            String query = "update info set height = ?, weight = ?, egg_group1 = ?, egg_group2 = ? where iid = ?";
            PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
            preparedStmt.setString(1, this.height.getText());
            preparedStmt.setString(2, this.weight.getText());
            preparedStmt.setString(3, this.egg_group1.getSelectedItem().toString());
            preparedStmt.setString(4, this.egg_group2.getSelectedItem().toString());
            preparedStmt.setInt(5, Integer.parseInt(this.id.getText()));

            preparedStmt.executeUpdate();
            DefaultTableModel tmodel = (DefaultTableModel) dex_table.getModel();
            int selectrowindex = dex_table.getSelectedRow();
            height.setText(tmodel.getValueAt(selectrowindex, 10).toString());
            weight.setText(tmodel.getValueAt(selectrowindex, 11).toString());
            this.egg_group1.setSelectedItem(tmodel.getValueAt(selectrowindex, 12).toString());
            this.egg_group2.setSelectedItem(tmodel.getValueAt(selectrowindex, 13).toString());
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        dex_table = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        info = new javax.swing.JPanel();
        egg_group1 = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        egg_group2 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        height = new javax.swing.JTextField();
        weight = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        pokemon = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        id = new javax.swing.JTextField();
        name = new javax.swing.JTextField();
        type1 = new javax.swing.JComboBox<>();
        type2 = new javax.swing.JComboBox<>();
        base_stats = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        hp = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        spd = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        atk = new javax.swing.JTextField();
        def = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        spa = new javax.swing.JTextField();
        spe = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        edit = new javax.swing.JButton();
        add = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        pagination1 = new pagination.Pagination();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pokedex");
        setBackground(new java.awt.Color(0, 0, 0));
        setPreferredSize(new java.awt.Dimension(1750, 970));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        dex_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "1st Type", "2nd Type", "HP", "Attack", "Defense", "Sp. Attack", "Sp. Defense", "Speed", "Height (m)", "Weight (kg)", "Egg Group 1", "Egg Group 2"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        dex_table.setAutoscrolls(false);
        dex_table.setRowHeight(84);
        dex_table.setSelectionBackground(new java.awt.Color(153, 153, 255));
        dex_table.setShowGrid(false);
        dex_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dex_tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(dex_table);
        if (dex_table.getColumnModel().getColumnCount() > 0) {
            dex_table.getColumnModel().getColumn(0).setPreferredWidth(40);
            dex_table.getColumnModel().getColumn(1).setPreferredWidth(120);
            dex_table.getColumnModel().getColumn(2).setPreferredWidth(60);
            dex_table.getColumnModel().getColumn(3).setPreferredWidth(60);
            dex_table.getColumnModel().getColumn(4).setPreferredWidth(40);
            dex_table.getColumnModel().getColumn(5).setPreferredWidth(40);
            dex_table.getColumnModel().getColumn(6).setPreferredWidth(40);
            dex_table.getColumnModel().getColumn(7).setPreferredWidth(40);
            dex_table.getColumnModel().getColumn(8).setPreferredWidth(40);
            dex_table.getColumnModel().getColumn(9).setPreferredWidth(40);
            dex_table.getColumnModel().getColumn(12).setPreferredWidth(120);
        }

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));

        info.setBackground(new java.awt.Color(255, 255, 255));
        info.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Info", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        info.setForeground(new java.awt.Color(255, 255, 255));
        info.setOpaque(false);

        egg_group1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---", "Monster", "Bug", "Flying", "Field", "Fairy", "Grass", "Human-Like", "Mineral", "Amorphous", "Water 1", "Water 2", "Water 3", "Dragon", "Ditto", "Undiscovered", "Unknown" }));
        egg_group1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                egg_group1ActionPerformed(evt);
            }
        });

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Egg Group:");

        egg_group2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---", "Monster", "Bug", "Flying", "Field", "Fairy", "Grass", "Human-Like", "Mineral", "Amorphous", "Water 1", "Water 2", "Water 3", "Dragon", "Ditto", "Undiscovered", "Unknown" }));
        egg_group2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                egg_group2ActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Height");

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Weight");

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("meters");

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("kilograms");

        javax.swing.GroupLayout infoLayout = new javax.swing.GroupLayout(info);
        info.setLayout(infoLayout);
        infoLayout.setHorizontalGroup(
            infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(egg_group1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(egg_group2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(infoLayout.createSequentialGroup()
                        .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel14)
                            .addGroup(infoLayout.createSequentialGroup()
                                .addComponent(height, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel13))
                            .addComponent(jLabel12)
                            .addGroup(infoLayout.createSequentialGroup()
                                .addComponent(weight, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel15)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        infoLayout.setVerticalGroup(
            infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, infoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(height, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(weight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(egg_group1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(egg_group2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        pokemon.setBackground(new java.awt.Color(255, 255, 255));
        pokemon.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Pokemon", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        pokemon.setForeground(new java.awt.Color(255, 255, 255));
        pokemon.setOpaque(false);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Primary Type");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Name");

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Secondary Type");

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID");

        id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idActionPerformed(evt);
            }
        });

        type1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---", "Normal", "Fighting", "Flying", "Electric", "Fire", "Water", "Grass", "Ground", "Ice", "Rock", "Steel", "Poison", "Psychic", "Ghost", "Dark", "Bug", "Dragon", "Fairy" }));
        type1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                type1ActionPerformed(evt);
            }
        });

        type2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---", "Normal", "Fighting", "Flying", "Electric", "Fire", "Water", "Grass", "Ground", "Ice", "Rock", "Steel", "Poison", "Psychic", "Ghost", "Dark", "Bug", "Dragon", "Fairy" }));

        javax.swing.GroupLayout pokemonLayout = new javax.swing.GroupLayout(pokemon);
        pokemon.setLayout(pokemonLayout);
        pokemonLayout.setHorizontalGroup(
            pokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pokemonLayout.createSequentialGroup()
                .addGroup(pokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pokemonLayout.createSequentialGroup()
                            .addGap(61, 61, 61)
                            .addComponent(jLabel2))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pokemonLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel4)))
                    .addGroup(pokemonLayout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pokemonLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(name, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                    .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(type1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(type2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(7, Short.MAX_VALUE))
        );
        pokemonLayout.setVerticalGroup(
            pokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pokemonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(type1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pokemonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(type2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        base_stats.setBackground(new java.awt.Color(255, 255, 255));
        base_stats.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Base Stats", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        base_stats.setForeground(new java.awt.Color(255, 255, 255));
        base_stats.setOpaque(false);

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Attack");

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("HP");

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Special Defense");

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Special Attack");

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Speed");

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Defense");

        javax.swing.GroupLayout base_statsLayout = new javax.swing.GroupLayout(base_stats);
        base_stats.setLayout(base_statsLayout);
        base_statsLayout.setHorizontalGroup(
            base_statsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(base_statsLayout.createSequentialGroup()
                .addGroup(base_statsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(base_statsLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jLabel11))
                    .addGroup(base_statsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(base_statsLayout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addGroup(base_statsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel8)
                                .addComponent(jLabel7)
                                .addComponent(jLabel9)))
                        .addGroup(base_statsLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel10))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(base_statsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(spd, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(spa, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(def, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(atk, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spe)
                    .addComponent(hp, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        base_statsLayout.setVerticalGroup(
            base_statsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(base_statsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(base_statsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(hp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(base_statsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(atk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(base_statsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(def, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(base_statsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(spa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(base_statsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(spd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(base_statsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), " ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        jPanel4.setOpaque(false);

        edit.setText("Update");
        edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });

        add.setText("Add");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(add, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(edit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(edit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pokemon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(base_stats, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(info, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pokemon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(base_stats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(info, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        pagination1.setOpaque(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(816, Short.MAX_VALUE)
                .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(618, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1370, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        addDex();
        addStats();
        addInfo();
        /*try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokemon","root","");  

            String query = " insert into dex (name, type1, type2)" + " values (?, ?, ?);"
                    + "insert into stats (hp, atk, def, spa, spd, spe)" + " values (?, ?, ?, ?, ?, ?);"
                    + "insert into info (height, weight, egg_group1, egg_group2" + "values (?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            
            // Table: dex
            preparedStmt.setString (1, this.name.getText());
            preparedStmt.setString (2, this.type1.getText());
            preparedStmt.setString (3, this.type2.getText());
            // Table: stats
            preparedStmt.setInt (4, Integer.parseInt(this.hp.getText()));
            preparedStmt.setInt (5, Integer.parseInt(this.atk.getText()));
            preparedStmt.setInt (6, Integer.parseInt(this.def.getText()));
            preparedStmt.setInt (7, Integer.parseInt(this.spa.getText()));
            preparedStmt.setInt (8, Integer.parseInt(this.spd.getText()));
            preparedStmt.setInt (9, Integer.parseInt(this.spe.getText()));
            // Table: info
            preparedStmt.setString (10, this.height.getText());
            preparedStmt.setString (11, this.weight.getText());
            preparedStmt.setString (12, this.egg_group1.getSelectedItem().toString());
            preparedStmt.setString (13, this.egg_group2.getSelectedItem().toString());
            preparedStmt.execute();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        /*
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokemon","root","");
            String query = " insert into stats (hp, atk, def, spa, spd, spe)" + " values (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt (1, Integer.parseInt(this.hp.getText()));
            preparedStmt.setInt (2, Integer.parseInt(this.atk.getText()));
            preparedStmt.setInt (3, Integer.parseInt(this.def.getText()));
            preparedStmt.setInt (4, Integer.parseInt(this.spa.getText()));
            preparedStmt.setInt (5, Integer.parseInt(this.spd.getText()));
            preparedStmt.setInt (6, Integer.parseInt(this.spe.getText()));
            preparedStmt.execute();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
            
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pokemon","root","");
            String query = " insert into info (height, weight, egg_group1, egg_group2)" + " values (?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString (1, this.height.getText());
            preparedStmt.setString (2, this.weight.getText());
            preparedStmt.setString (2, this.egg_group1.getSelectedItem().toString());
            preparedStmt.setString (2, this.egg_group2.getSelectedItem().toString());
            preparedStmt.execute();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        DefaultTableModel model = (DefaultTableModel) dex_table.getModel();
        model.setRowCount(0);
        updateTable(1);
        clear();
    }//GEN-LAST:event_addActionPerformed

    private void editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editActionPerformed
        /*
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","");  

            String query = "update users set last_name = ?, first_name = ?, age = ?, address = ? where id = ?";
            PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
            preparedStmt.setString(1, this.last_name.getText());
            preparedStmt.setString(2, this.first_name.getText());
            preparedStmt.setInt(3, Integer.parseInt(this.age.getText()));
            preparedStmt.setString(4, this.address.getText());
            preparedStmt.setInt(5, Integer.parseInt(this.id.getText()));
            preparedStmt.executeUpdate();
            DefaultTableModel tmodel = (DefaultTableModel) dataTable.getModel();
            int selectrowindex = dataTable.getSelectedRow();
            id.setText(tmodel.getValueAt(selectrowindex, 0).toString());
            last_name.setText(tmodel.getValueAt(selectrowindex, 1).toString());
            first_name.setText(tmodel.getValueAt(selectrowindex, 2).toString());
            age.setText(tmodel.getValueAt(selectrowindex, 3).toString());
            address.setText(tmodel.getValueAt(selectrowindex, 4).toString());
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        */
        updateDex();
        updateStats();
        updateInfo();
        DefaultTableModel model = (DefaultTableModel) dex_table.getModel();
        model.setRowCount(0);
        updateTable(1);
        clear();
    }//GEN-LAST:event_editActionPerformed

    private void egg_group1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_egg_group1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_egg_group1ActionPerformed

    private void egg_group2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_egg_group2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_egg_group2ActionPerformed

    private void dex_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dex_tableMouseClicked
        DefaultTableModel tmodel = (DefaultTableModel) dex_table.getModel();
        int selectrowindex = dex_table.getSelectedRow();
        id.setText(tmodel.getValueAt(selectrowindex, 0).toString());
        name.setText(tmodel.getValueAt(selectrowindex, 1).toString());
        type1.setSelectedItem(tmodel.getValueAt(selectrowindex, 2).toString());
        type2.setSelectedItem(tmodel.getValueAt(selectrowindex, 3).toString());
        hp.setText(tmodel.getValueAt(selectrowindex, 4).toString());
        atk.setText(tmodel.getValueAt(selectrowindex, 5).toString());
        def.setText(tmodel.getValueAt(selectrowindex, 6).toString());
        spa.setText(tmodel.getValueAt(selectrowindex, 7).toString());
        spd.setText(tmodel.getValueAt(selectrowindex, 8).toString());
        spe.setText(tmodel.getValueAt(selectrowindex, 9).toString());
        height.setText(tmodel.getValueAt(selectrowindex, 10).toString());
        weight.setText(tmodel.getValueAt(selectrowindex, 11).toString());
        this.egg_group1.setSelectedItem(tmodel.getValueAt(selectrowindex, 12).toString());
        this.egg_group2.setSelectedItem(tmodel.getValueAt(selectrowindex, 13).toString());
    }//GEN-LAST:event_dex_tableMouseClicked

    private void type1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_type1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_type1ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            updateTable(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_formWindowOpened

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
            java.util.logging.Logger.getLogger(PokemonDB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PokemonDB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PokemonDB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PokemonDB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PokemonDB().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JTextField atk;
    private javax.swing.JPanel base_stats;
    private javax.swing.JTextField def;
    private javax.swing.JTable dex_table;
    private javax.swing.JButton edit;
    private javax.swing.JComboBox<String> egg_group1;
    private javax.swing.JComboBox<String> egg_group2;
    private javax.swing.JTextField height;
    private javax.swing.JTextField hp;
    private javax.swing.JTextField id;
    private javax.swing.JPanel info;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField name;
    private pagination.Pagination pagination1;
    private javax.swing.JPanel pokemon;
    private javax.swing.JTextField spa;
    private javax.swing.JTextField spd;
    private javax.swing.JTextField spe;
    private javax.swing.JComboBox<String> type1;
    private javax.swing.JComboBox<String> type2;
    private javax.swing.JTextField weight;
    // End of variables declaration//GEN-END:variables
}
