package prakPemlan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Form extends JFrame {

    private JTextField nama;
    private JTextField tanggalLahir;
    private JTextField nomor;
    private JTextField noTelp;
    private JTextField alamat;
    private JTextField email;

    public Form() {
        formRegistrasi();
    }

    public void formRegistrasi() {
        setTitle("Form Pendaftaran Ulang");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel labelNama = new JLabel("Nama");
        nama = new JTextField(20);
        JLabel labelTgl = new JLabel("Tanggal Lahir");
        tanggalLahir = new JTextField(20);
        JLabel labelNomor = new JLabel("Nomor Pendaftaran");
        nomor = new JTextField(20);
        JLabel labelTelepon = new JLabel("N0. Telp");
        noTelp = new JTextField(20);
        JLabel labelAlamat = new JLabel("Alamat");
        alamat = new JTextField(20);
        JLabel labelEmail = new JLabel("E-mail");
        email = new JTextField(20);

        JButton submit = new JButton("Submit");
        JButton tampilkanData = new JButton("Tampilkan Data");

        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(labelNama, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nama, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(labelTgl, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(tanggalLahir, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(labelNomor, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(nomor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(labelTelepon, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(noTelp, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(labelAlamat, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(alamat, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(labelEmail, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(email, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(submit, gbc);

        gbc.gridy = 7;
        panel.add(tampilkanData, gbc);

        add(panel);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hasilSubmit();
            }
        });

        tampilkanData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tampilkanData();
            }
        });
    }

    private void hasilSubmit() {
        if (nama.getText().isEmpty() || tanggalLahir.getText().isEmpty()
                || nomor.getText().isEmpty() || noTelp.getText().isEmpty()
                || alamat.getText().isEmpty() || email.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua kolom wajib diisi!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int hasil = JOptionPane.showConfirmDialog(this, "Apakah anda yakin data yang Anda isi sudah benar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (hasil == JOptionPane.YES_OPTION) {
                if (saveDataToDatabase()) {
                    JOptionPane.showMessageDialog(this, "Data berhasil disimpan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menyimpan data ke database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private boolean saveDataToDatabase() {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mahasiswa_db", "root", "revan1055");

            String query = "INSERT INTO mahasiswa (nama, tanggal_lahir, nomor_pendaftaran, no_telp, alamat, email) VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, nama.getText());
            statement.setString(2, tanggalLahir.getText());
            statement.setString(3, nomor.getText());
            statement.setString(4, noTelp.getText());
            statement.setString(5, alamat.getText());
            statement.setString(6, email.getText());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void tampilkanData() {
        JFrame frameHasil = new JFrame("Data Mahasiswa");
        frameHasil.setSize(600, 400);
        frameHasil.setLocationRelativeTo(null);
        frameHasil.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Data Mahasiswa");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton tombolTutup = new JButton("Tutup");
        tombolTutup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameHasil.dispose();
            }
        });

        JPanel panelTombol = new JPanel();
        panelTombol.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panelTombol.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelTombol.add(tombolTutup);

        panel.add(panelTombol, BorderLayout.SOUTH);

        frameHasil.add(panel);

        fetchDataFromDatabase(textArea);

        frameHasil.setVisible(true);
    }

    private void fetchDataFromDatabase(JTextArea textArea) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mahasiswa_db", "root", "revan1055");

            String query = "SELECT * FROM mahasiswa";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            textArea.setText("");

            while (resultSet.next()) {
                String data = String.format("Nama: %s\nTanggal Lahir: %s\nNomor Pendaftaran: %s\nNo. Telepon: %s\nAlamat: %s\nEmail: %s\n\n",
                        resultSet.getString("nama"),
                        resultSet.getString("tanggal_lahir"),
                        resultSet.getString("nomor_pendaftaran"),
                        resultSet.getString("no_telp"),
                        resultSet.getString("alamat"),
                        resultSet.getString("email"));
                textArea.append(data);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
