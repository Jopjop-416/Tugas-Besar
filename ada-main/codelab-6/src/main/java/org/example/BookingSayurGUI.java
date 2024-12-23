package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.nio.file.*;
import java.util.ArrayList;

public class BookingSayurGUI {
    // Nama file untuk menyimpan data pesanan
    private static final String FILE_NAME = "pesanan.txt";

    // Model tabel untuk menampilkan data di GUI
    private static DefaultTableModel tableModel;

    // Daftar untuk menyimpan data pesanan secara sementara
    private static List<Pesanan> daftarPesanan = new ArrayList<>();

    public static void main(String[] args) {
        // Membuat frame utama aplikasi
        JFrame frame = new JFrame("Booking Sayur App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Menutup aplikasi saat frame ditutup
        frame.setSize(800, 400); // Mengatur ukuran frame
        frame.setLocationRelativeTo(null); // Menempatkan frame di tengah layar

        // Panel utama untuk menampung semua elemen GUI
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE); // Warna latar belakang utama

        // Panel untuk form input
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.LIGHT_GRAY); // Warna latar belakang form
        GridBagConstraints gbc = new GridBagConstraints(); // Mengatur tata letak grid
        gbc.insets = new Insets(5, 20, 5, 20); // Margin antar elemen
        gbc.fill = GridBagConstraints.HORIZONTAL; // Elemen diperluas secara horizontal

        // Komponen label dan text field untuk form input
        JLabel namaLabel = new JLabel("Nama Pembeli:");
        JTextField namaField = new JTextField(15);
        JLabel alamatLabel = new JLabel("Alamat:");
        JTextField alamatField = new JTextField(15);
        JLabel pesananLabel = new JLabel("Pesanan:");
        JTextField pesananField = new JTextField(15);
        JLabel jumlahLabel = new JLabel("Jumlah (kg):");
        JTextField jumlahField = new JTextField(15);

        // Tombol untuk menambah, menghapus, memperbarui, dan menyimpan data
        JButton addButton = new JButton("Tambah Pesanan");
        addButton.setBackground(new Color(0, 153, 255)); // Warna tombol
        addButton.setForeground(Color.WHITE); // Warna teks tombol

        JButton deleteButton = new JButton("Hapus Pesanan");
        deleteButton.setBackground(new Color(229, 4, 58));
        deleteButton.setForeground(Color.WHITE);

        JButton updateButton = new JButton("Update Pesanan");
        updateButton.setBackground(new Color(50, 97, 182));
        updateButton.setForeground(Color.WHITE);

        JButton saveButton = new JButton("Simpan Pesanan");
        saveButton.setBackground(new Color(41, 217, 41));
        saveButton.setForeground(Color.WHITE);

        // Menambahkan elemen ke panel input menggunakan GridBagConstraints
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(namaLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(namaField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(alamatLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(alamatField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(pesananLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(pesananField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(jumlahLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(jumlahField, gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        inputPanel.add(addButton, gbc);
        gbc.gridy = 5;
        inputPanel.add(updateButton, gbc);
        gbc.gridy = 6;
        inputPanel.add(deleteButton, gbc);
        gbc.gridy = 7;
        inputPanel.add(saveButton, gbc);

        // Kolom tabel
        String[] columns = {"Nama Pembeli", "Alamat", "Pesanan", "Jumlah (kg)"};
        tableModel = new DefaultTableModel(columns, 0); // Model tabel dengan kolom yang ditentukan
        JTable table = new JTable(tableModel); // Membuat tabel dengan model tersebut
        JScrollPane tableScrollPane = new JScrollPane(table); // Panel scroll untuk tabel
        tableScrollPane.setPreferredSize(new Dimension(400, 300)); // Ukuran panel scroll

        // Tombol tambah pesanan
        addButton.addActionListener(e -> {
            // Mengambil data dari text field
            String nama = namaField.getText();
            String alamat = alamatField.getText();
            String pesanan = pesananField.getText();
            int jumlah;

            try {
                // Mengubah teks jumlah menjadi angka
                jumlah = Integer.parseInt(jumlahField.getText());
                if (nama.isEmpty() || alamat.isEmpty() || pesanan.isEmpty()) {
                    // Validasi jika ada field kosong
                    JOptionPane.showMessageDialog(frame, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Membuat objek pesanan baru
                    Pesanan newPesanan = new Pesanan(nama, alamat, pesanan, jumlah);
                    daftarPesanan.add(newPesanan); // Menambah ke daftar
                    tableModel.addRow(new Object[]{nama, alamat, pesanan, jumlah}); // Menambah ke tabel
                    clearFields(namaField, alamatField, pesananField, jumlahField); // Mengosongkan field input
                }
            } catch (NumberFormatException ex) {
                // Validasi jika jumlah bukan angka
                JOptionPane.showMessageDialog(frame, "Jumlah harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Tombol hapus pesanan
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow(); // Mengambil baris yang dipilih
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow); // Hapus data dari tabel
                daftarPesanan.remove(selectedRow); // Hapus data dari daftar
            } else {
                JOptionPane.showMessageDialog(frame, "Pilih pesanan yang akan dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Tombol update pesanan
        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow(); // Mengambil baris yang dipilih
            if (selectedRow != -1) {
                Pesanan pesanan = daftarPesanan.get(selectedRow); // Mengambil data dari daftar

                // Menampilkan dialog untuk input data baru
                String newNama = JOptionPane.showInputDialog(frame, "Masukkan nama baru:", pesanan.getNamaPembeli());
                String newAlamat = JOptionPane.showInputDialog(frame, "Masukkan alamat baru:", pesanan.getAlamat());
                String newPesanan = JOptionPane.showInputDialog(frame, "Masukkan pesanan baru:", pesanan.getPesanan());
                String newJumlahStr = JOptionPane.showInputDialog(frame, "Masukkan jumlah baru (kg):", pesanan.getJumlah());

                try {
                    int newJumlah = Integer.parseInt(newJumlahStr); // Validasi jumlah baru
                    if (!newNama.isEmpty() && !newAlamat.isEmpty() && !newPesanan.isEmpty()) {
                        // Update data di objek pesanan dan tabel
                        pesanan.setNamaPembeli(newNama);
                        pesanan.setAlamat(newAlamat);
                        pesanan.setPesanan(newPesanan);
                        pesanan.setJumlah(newJumlah);

                        tableModel.setValueAt(newNama, selectedRow, 0);
                        tableModel.setValueAt(newAlamat, selectedRow, 1);
                        tableModel.setValueAt(newPesanan, selectedRow, 2);
                        tableModel.setValueAt(newJumlah, selectedRow, 3);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Data tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Jumlah harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Pilih pesanan yang akan diupdate!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Tombol simpan data ke file
        saveButton.addActionListener(e -> savePesananToFile());

        // Menambahkan panel ke frame utama
        mainPanel.add(inputPanel, BorderLayout.WEST);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        frame.add(mainPanel);
        frame.setVisible(true); // Menampilkan frame
    }

    // Fungsi untuk mengosongkan text field
    private static void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    // Fungsi untuk menyimpan data ke file
    private static void savePesananToFile() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_NAME))) {
            for (Pesanan pesanan : daftarPesanan) {
                writer.write(pesanan.getNamaPembeli() + "," +
                        pesanan.getAlamat() + "," +
                        pesanan.getPesanan() + "," +
                        pesanan.getJumlah());
                writer.newLine();
            }
            JOptionPane.showMessageDialog(null, "Pesanan berhasil disimpan!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan pesanan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Kelas untuk data pesanan
    static class Pesanan {
        private String namaPembeli;
        private String alamat;
        private String pesanan;
        private int jumlah;

        public Pesanan(String namaPembeli, String alamat, String pesanan, int jumlah) {
            this.namaPembeli = namaPembeli;
            this.alamat = alamat;
            this.pesanan = pesanan;
            this.jumlah = jumlah;
        }

        public String getNamaPembeli() {
            return namaPembeli;
        }

        public void setNamaPembeli(String namaPembeli) {
            this.namaPembeli = namaPembeli;
        }

        public String getAlamat() {
            return alamat;
        }

        public void setAlamat(String alamat) {
            this.alamat = alamat;
        }

        public String getPesanan() {
            return pesanan;
        }

        public void setPesanan(String pesanan) {
            this.pesanan = pesanan;
        }

        public int getJumlah() {
            return jumlah;
        }

        public void setJumlah(int jumlah) {
            this.jumlah = jumlah;
        }
    }
}
