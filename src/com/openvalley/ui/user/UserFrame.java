package com.openvalley.ui.user;

import com.openvalley.server.entity.User;
import com.openvalley.server.mapper.UserMapper;
import com.openvalley.server.mapper.impl.UserMapperImpl;
import com.openvalley.ui.base.UIConstants;
import com.openvalley.ui.medicine.MedicineFrame;
import com.openvalley.ui.base.UIConverter;

import javax.swing.*;
import java.awt.*;

public class UserFrame extends JFrame {

    JPanel panel;

    private JLabel titleLabel;

    private JLabel usernameLabel;

    private JLabel passwordLabel;

    private JTextField username;

    private JPasswordField password;

    private JButton login;
    private JButton register;

    private final transient UserMapper userMapper = new UserMapperImpl();


    public UserFrame() {
        initPanel();
        initFrame();
        loginButtonListener();
        registerButtonListener();
    }

    private void initFrame() {
        UIConverter.initFrame(this, ("登录"));
    }

    private void initPanel() {
        panel = new JPanel();
        this.setContentPane(panel);
        this.setLayout(null);

        initLabel();
        panel.add(titleLabel);
        panel.add(usernameLabel);
        panel.add(passwordLabel);

        initField();
        panel.add(username);
        panel.add(password);

        initButton();
        panel.add(login);
        panel.add(register);
    }

    private void initLabel() {
        // 1. 标题标签
        titleLabel = new JLabel("医药管理系统", SwingConstants.CENTER);  // 创建一个标签,设置水平居中
        titleLabel.setForeground(Color.BLUE);                               // 设置字体颜色
        titleLabel.setFont(new Font(UIConstants.FONT_NAME_SONG, Font.PLAIN, 50));     // 设置字体大小
        titleLabel.setSize(800, 100);

        // 2.用户名标签
        usernameLabel = new JLabel("用户名:");
        usernameLabel.setForeground(new Color(0xFF0000));
        usernameLabel.setFont(new Font(UIConstants.FONT_NAME_SONG, Font.PLAIN, 30));
        usernameLabel.setBounds(200, 100, 200, 100);

        // 3.登录标签
        passwordLabel = new JLabel("密码  :");
        passwordLabel.setForeground(new Color(0xFF0000));
        passwordLabel.setFont(new Font(UIConstants.FONT_NAME_SONG, Font.PLAIN, 30));
        passwordLabel.setBounds(200, 160, 200, 100);
    }

    private void initField() {
        // 1.用户名输入框
        username = new JTextField(20);
        username.setFont(new Font(UIConstants.FONT_NAME_SONG, Font.PLAIN, 18));
        username.setSelectedTextColor(new Color(0xFF0000));
        username.setBounds(330, 132, 280, 40);

        // 2.密码输入框
        password = new JPasswordField(20);
        password.setBounds(330, 192, 280, 40);
    }

    private void initButton() {
        // 1.登录按钮
        login = UIConverter.initButton("登录", Color.BLUE, Color.GREEN);
        login.setBounds(270, 300, 100, 50);

        // 2.注册按钮
        register = UIConverter.initButton("注册", Color.BLUE, Color.PINK);
        register.setBounds(450, 300, 100, 50);
    }

    private void loginButtonListener() {
        login.addActionListener(ae -> {
            String usernameStr = username.getText();
            String passwordStr = new String(password.getPassword());
            User user = userMapper.selectByUsername(usernameStr);

            if (user == null) {
                JOptionPane.showMessageDialog(this, "用户名不存在,请注册!");
            }

            if (user != null) {
                if (user.getPassword().equals(passwordStr)) {
                    new MedicineFrame();
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "登录失败,账号或密码错误!");
                }
            }
        });
    }

    private void registerButtonListener() {
        register.addActionListener(ae -> {
            String usernameStr = username.getText();
            String passwordStr = new String(password.getPassword());

            User user = userMapper.selectByUsername(usernameStr);
            if (user != null) {
                JOptionPane.showMessageDialog(this, "用户已存在,请登录或重新注册!");
            } else {
                User entity = new User();
                entity.setUsername(usernameStr);
                entity.setPassword(passwordStr);
                entity.setType("0");
                userMapper.insert(entity);
                JOptionPane.showMessageDialog(this, "用户注册成功!");
                password.setText(null);
                username.setText(null);
            }
        });
    }
}
