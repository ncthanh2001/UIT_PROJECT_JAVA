package org.smart_job.view;

import org.smart_job.common.Response;
import org.smart_job.entity.User;
import org.smart_job.service.UserService;
import org.smart_job.service.impl.UserServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class UserManagementFrame extends JFrame {

    private final UserService userService = new UserServiceImpl();
    private JTable userTable;
    private DefaultTableModel tableModel;

    private JTextField tfUsername, tfEmail, tfPassword;
    private JButton btnAdd, btnUpdate, btnDelete, btnRefresh;

    public UserManagementFrame() {
        setTitle("User Management");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();
        loadUserTable();
        initListeners();
    }

    private void initComponents() {
        // Table setup
        String[] columns = {"ID", "Username", "Email"};
        tableModel = new DefaultTableModel(columns, 0);
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);

        // Form inputs
        tfUsername = new JTextField(15);
        tfEmail = new JTextField(15);
        tfPassword = new JTextField(15);

        JPanel formPanel = new JPanel(new GridLayout(2, 4, 10, 5));
        formPanel.add(new JLabel("Username:"));
        formPanel.add(tfUsername);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(tfEmail);
        formPanel.add(new JLabel("Password:"));
        formPanel.add(tfPassword);

        // Buttons
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnRefresh = new JButton("Refresh");

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnRefresh);

        // Layout
        setLayout(new BorderLayout(10, 10));
        add(scrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void initListeners() {
        btnAdd.addActionListener(e -> handleAdd());
        btnUpdate.addActionListener(e -> handleUpdate());
        btnDelete.addActionListener(e -> handleDelete());
        btnRefresh.addActionListener(e -> loadUserTable());

        // fill form when select row
        userTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = userTable.getSelectedRow();
                if (row >= 0) {
                    tfUsername.setText(tableModel.getValueAt(row, 1).toString());
                    tfEmail.setText(tableModel.getValueAt(row, 2).toString());
                }
            }
        });
    }

    private void handleAdd() {
        String username = tfUsername.getText();
        String email = tfEmail.getText();
        String password = tfPassword.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showError("Vui lòng nhập đầy đủ thông tin.");
            return;
        }

        User user = new User();
        user.setUserName(username);
        user.setEmail(email);
        user.setPassword(password);

        Response<User> res = userService.register(user);
        if (res.isSuccess()) {
            showMessage("Thêm user thành công");
            clearForm();
            loadUserTable();
        } else {
            showError(res.getMessage());
        }
    }

    private void handleUpdate() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow < 0) {
            showError("Chọn user cần cập nhật.");
            return;
        }

        Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
        String username = tfUsername.getText();
        String email = tfEmail.getText();
        String password = tfPassword.getText();

        if (username.isEmpty() || email.isEmpty()) {
            showError("Không được để trống username/email.");
            return;
        }

        User user = new User();
        user.setId(id);
        user.setUserName(username);
        user.setEmail(email);
        user.setPassword(password);

        Response<User> res = userService.updateProfile(user);
        if (res.isSuccess()) {
            showMessage("Cập nhật thành công");
            clearForm();
            loadUserTable();
        } else {
            showError(res.getMessage());
        }
    }

    private void handleDelete() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow < 0) {
            showError("Chọn user để xoá.");
            return;
        }

        Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xoá user ID = " + id + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Response<Boolean> res = userService.deleteUser(id);
            if (res.isSuccess()) {
                showMessage("Đã xoá thành công.");
                loadUserTable();
            } else {
                showError(res.getMessage());
            }
        }
    }

    private void loadUserTable() {
        tableModel.setRowCount(0);
        Response<List<User>> res = userService.getAllUsers();
        if (res.isSuccess()) {
            for (User u : res.getData()) {
                tableModel.addRow(new Object[]{u.getId(), u.getUserName(), u.getEmail()});
            }
        } else {
            showError(res.getMessage());
        }
    }

    private void clearForm() {
        tfUsername.setText("");
        tfEmail.setText("");
        tfPassword.setText("");
        userTable.clearSelection();
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserManagementFrame().setVisible(true));
    }
}
