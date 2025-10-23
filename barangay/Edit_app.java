/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package barangay;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
public class Edit_app extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Edit_app.class.getName());

    /**
     * Creates new form Edit_app
     */
    public Edit_app() {
        initComponents();
        loadDoctorsToComboBox();
        loadDoctorsToComboBox(); // From previous code
    loadTimeComboBox();
    loadStatusComboBox();
         name.setText(String.valueOf(UserSession.getFirstname()));
        
    }
    
    
     public void loadDoctorsToComboBox() {
        try {
            // Database connection
            String url = "jdbc:mysql://localhost:3306/appointment";
            String user = "root";
            String password = "";
            
            Connection conn = DriverManager.getConnection(url, user, password);
            
            // Query to get all doctors
            String query = "SELECT Dr_name FROM tbl_doctor ORDER BY Dr_name";
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            // Clear existing items first
            doctorComboBox.removeAllItems();
            
            // Add a default option
            doctorComboBox.addItem("-- Select Doctor --");
            
            // Add all doctors from database
            while (rs.next()) {
                String doctorName = rs.getString("Dr_name");
                doctorComboBox.addItem(doctorName);
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading doctors: " + e.getMessage());
        }
    }

    public void loadAppointmentData(int appointmentID) {
        try {
            String url = "jdbc:mysql://localhost:3306/appointment";
            String user = "root";
            String password = "";
            
            Connection conn = DriverManager.getConnection(url, user, password);
            
            // Query to get appointment details
            String query = "SELECT a.AppointmentID, p.Last_name, a.Drname, a.Date, a.Time, a.Status " +
                           "FROM appointment_date a " +
                           "INNER JOIN patient_info p ON a.Patient_ID = p.Patient_ID " +
                           "WHERE a.AppointmentID = ?";
            
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, appointmentID);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                // Set Patient Name in the label
                String patientName = rs.getString("Last_name");
                name.setText(patientName);
                
                // Set Doctor in combo box
                String doctor = rs.getString("Drname");
                doctorComboBox.setSelectedItem(doctor);
                
                // Set Date
                String dateStr = rs.getString("Date");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = sdf.parse(dateStr);
                dateChooser.setDate(date);
                
                // Set Time in combo box
                String time = rs.getString("Time");
                timeComboBox.setSelectedItem(time);
                
                // Set Status in combo box
                String status = rs.getString("Status");
                if (status == null || status.trim().isEmpty()) {
                    status = "Pending";
                }
                statusComboBox.setSelectedItem(status);
            }
            
            rs.close();
            pst.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading appointment: " + e.getMessage());
        }
    }

    // Method to populate Time ComboBox
    public void loadTimeComboBox() {
        timeComboBox.removeAllItems();
        
        // Add time options
        String[] times = {
            "8:00 AM", "8:30 AM", "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM",
            "11:00 AM", "11:30 AM", "12:00 PM", "12:30 PM", "1:00 PM", "1:30 PM",
            "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM",
            "5:00 PM", "5:30 PM", "6:00 PM"
        };
        
        for (String time : times) {
            timeComboBox.addItem(time);
        }
    }

    // Method to populate Status ComboBox
    public void loadStatusComboBox() {
        statusComboBox.removeAllItems();
        
        statusComboBox.addItem("Pending");
        statusComboBox.addItem("Confirmed");
        statusComboBox.addItem("Completed");
        statusComboBox.addItem("Cancelled");
    }
// Save changes button action
private void saveChangesButtonActionPerformed(java.awt.event.ActionEvent evt) {
  
    try {
        // Get Appointment ID from the LABEL
        String idText = name.getText().trim(); // Replace 'lblAppointmentID' with your label name
        
        // Check if empty
        if (idText == null || idText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Appointment ID is missing!");
            return;
        }
        
        int appointmentID = Integer.parseInt(idText);
        
        // Get Doctor
        String doctor = (String) doctorComboBox.getSelectedItem();
        
        // Get Date
        if (dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Please select a date!");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(dateChooser.getDate());
        
        // Get Time
        String time = (String) timeComboBox.getSelectedItem();
        
        // Get Status
        String status = (String) statusComboBox.getSelectedItem();
        
        // Validation
        if (doctor == null || doctor.equals("-- Select Doctor --") || doctor.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a doctor!");
            return;
        }
        
        if (time == null || time.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a time!");
            return;
        }
        
        if (status == null || status.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a status!");
            return;
        }
        
        // Database connection
        String url = "jdbc:mysql://localhost:3306/appointment";
        String user = "root";
        String password = "";
        
        Connection conn = DriverManager.getConnection(url, user, password);
        
        // Update query
        String updateQuery = "UPDATE appointment_date SET Dr_name = ?, Date = ?, Time = ?, Status = ? WHERE AppointmentID = ?";
        PreparedStatement pst = conn.prepareStatement(updateQuery);
        
        pst.setString(1, doctor);
        pst.setString(2, date);
        pst.setString(3, time);
        pst.setString(4, status);
        pst.setInt(5, appointmentID);
        
        int updated = pst.executeUpdate();
        
        if (updated > 0) {
            JOptionPane.showMessageDialog(null, "Appointment updated successfully!");
            this.dispose(); // Close the edit form
        } else {
            JOptionPane.showMessageDialog(null, "Failed to update. Appointment ID not found!");
        }
        
        pst.close();
        conn.close();
        
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Invalid Appointment ID format!");
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
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

        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        Name = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        timeComboBox = new javax.swing.JComboBox<>();
        statusComboBox = new javax.swing.JComboBox<>();
        dateChooser = new com.toedter.calendar.JDateChooser();
        doctorComboBox = new javax.swing.JComboBox<>();
        name = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        paname = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();

        jLabel10.setText("jLabel10");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));

        Name.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Snap ITC", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 102));
        jLabel3.setText("Patient Name");

        jLabel4.setFont(new java.awt.Font("Snap ITC", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("Doctor");

        jLabel5.setFont(new java.awt.Font("Snap ITC", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setText("Date");

        jLabel6.setFont(new java.awt.Font("Snap ITC", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 102));
        jLabel6.setText("Time");

        jLabel7.setFont(new java.awt.Font("Snap ITC", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 102));
        jLabel7.setText("Status");

        timeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "9 : 00 AM", "10 : 30 AM", "1 : 00 PM", "2 : 30 PM", "3 : 30 PM", "4 : 30 AM" }));
        timeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeComboBoxActionPerformed(evt);
            }
        });

        statusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Confirmed", "Pending", "Cancelled" }));
        statusComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusComboBoxActionPerformed(evt);
            }
        });

        doctorComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", " ", " " }));
        doctorComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doctorComboBoxActionPerformed(evt);
            }
        });

        name.setOpaque(true);

        jLabel8.setOpaque(true);

        javax.swing.GroupLayout NameLayout = new javax.swing.GroupLayout(Name);
        Name.setLayout(NameLayout);
        NameLayout.setHorizontalGroup(
            NameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NameLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(NameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5))
                .addGap(116, 116, 116)
                .addGroup(NameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(doctorComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(timeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(NameLayout.createSequentialGroup()
                        .addComponent(paname, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(116, 116, 116))
                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        NameLayout.setVerticalGroup(
            NameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NameLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(NameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(NameLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(NameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, NameLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(paname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(NameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(doctorComboBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(NameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(NameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(timeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(NameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(statusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Snap ITC", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 102));
        jLabel1.setText("Edit Appointment");

        jButton17.setBackground(new java.awt.Color(0, 204, 204));
        jButton17.setFont(new java.awt.Font("Snap ITC", 0, 12)); // NOI18N
        jButton17.setForeground(new java.awt.Color(255, 255, 255));
        jButton17.setText("Save changes");
        jButton17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204), 2));
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setBackground(new java.awt.Color(0, 204, 204));
        jButton18.setFont(new java.awt.Font("Snap ITC", 0, 12)); // NOI18N
        jButton18.setForeground(new java.awt.Color(255, 255, 255));
        jButton18.setText("Back");
        jButton18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204), 2));
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)
                        .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton17)
                    .addComponent(jButton18))
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void timeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_timeComboBoxActionPerformed

    private void doctorComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doctorComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_doctorComboBoxActionPerformed

    private void statusComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statusComboBoxActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
try {
        // Get Appointment ID from the LABEL
        String idText = name.getText().trim(); // Replace 'lblAppointmentID' with your label name
        
        // Check if empty
        if (idText == null || idText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Appointment ID is missing!");
            return;
        }
        
        int appointmentID = Integer.parseInt(idText);
        
        // Get Doctor
        String doctor = (String) doctorComboBox.getSelectedItem();
        
        // Get Date
        if (dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Please select a date!");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(dateChooser.getDate());
        
        // Get Time
        String time = (String) timeComboBox.getSelectedItem();
        
        // Get Status
        String status = (String) statusComboBox.getSelectedItem();
        
        // Validation
        if (doctor == null || doctor.equals("-- Select Doctor --") || doctor.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a doctor!");
            return;
        }
        
        if (time == null || time.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a time!");
            return;
        }
        
        if (status == null || status.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a status!");
            return;
        }
        
        // Database connection
        String url = "jdbc:mysql://localhost:3306/appointment";
        String user = "root";
        String password = "";
        
        Connection conn = DriverManager.getConnection(url, user, password);
        
        // Update query
        String updateQuery = "UPDATE appointment_date SET Dr_name = ?, Date = ?, Time = ?, Status = ? WHERE AppointmentID = ?";
        PreparedStatement pst = conn.prepareStatement(updateQuery);
        
        pst.setString(1, doctor);
        pst.setString(2, date);
        pst.setString(3, time);
        pst.setString(4, status);
        pst.setInt(5, appointmentID);
        
        int updated = pst.executeUpdate();
        
        if (updated > 0) {
            JOptionPane.showMessageDialog(null, "Appointment updated successfully!");
            this.dispose(); // Close the edit form
        } else {
            JOptionPane.showMessageDialog(null, "Failed to update. Appointment ID not found!");
        }
        
        pst.close();
        conn.close();
        
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Invalid Appointment ID format!");
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    }
       
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
                     Admin ad = new Admin();
                     ad.show();
                     this.dispose();
    }//GEN-LAST:event_jButton18ActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Edit_app().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Name;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JComboBox<String> doctorComboBox;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel name;
    private javax.swing.JTextField paname;
    private javax.swing.JComboBox<String> statusComboBox;
    private javax.swing.JComboBox<String> timeComboBox;
    // End of variables declaration//GEN-END:variables
}
