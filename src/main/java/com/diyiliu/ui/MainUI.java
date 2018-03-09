package com.diyiliu.ui;

import com.diyiliu.support.cache.ICache;
import com.diyiliu.support.config.Constant;
import com.diyiliu.support.model.BetDetail;
import com.diyiliu.support.model.BetRecord;
import com.diyiliu.support.model.BetReturn;
import com.diyiliu.support.site.WebContainer;
import com.diyiliu.support.util.UIHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description: MainUI
 * Author: DIYILIU
 * Update: 2018-02-28 22:29
 */
public class MainUI extends javax.swing.JFrame {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final String lotteryType = "幸运飞艇";

    private boolean autoBet;

    @Resource
    private HelpWinUtil helpWinUtil;

    @Resource
    private WebContainer webContainer;

    @Resource
    private ICache agentCacheProvider;

    @Resource
    private ICache betCacheProvider;

    @Resource
    private LoginUI loginUI;

    /**
     * Creates new form MainUI
     */
    public MainUI() {
        initComponents();
        UIHelper.setCenter(this);
    }

    public MainUI(WebContainer webContainer) {
        UIHelper.beautify(Constant.LOOK_STYLE);
        this.webContainer = webContainer;
        initComponents();
        UIHelper.setCenter(this);

        lbUser.setText("-");
        lbMoney.setText("-");
        lbTodayWin.setText("0");

        lbRefresh.setIcon(new javax.swing.ImageIcon(ClassLoader.getSystemResource("image/synchronize.png"))); // NOI18N

        lbCurrentPeriod.setText("-");
        lbCurrentSum.setText("0");

        lbStatus.setText("获取中");

        lbLastPeriod.setText("-");
        lbLastResult.setText("无");

        tfCurrentBet.setText("");
        tfPlan.setText("");

        lbSumMoney.setText("0");

        tfPlan.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    calcMoney();
                }
            }
        });

        tfUnit.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    calcMoney();
                }
            }
        });

        // 更新金额数据
        lbRefresh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String balance = webContainer.getBalance();
                if (!balance.equals("?")) {
                    lbMoney.setText(balance);
                }
                setTodayWin();
            }
        });

        // 注销登录
        btnLogout.addActionListener(actionEvent -> {
            this.setVisible(false);

            webContainer.logout();
            agentCacheProvider.clear();
            betCacheProvider.clear();
            loginUI.init();
        });

        // 提交下注
        btnSubmit.addActionListener(actionEvent -> {
            String plan = tfPlan.getText().trim();
            String unit = tfUnit.getText().trim();

            if (StringUtils.isEmpty(plan) || StringUtils.isEmpty(unit)) {

                return;
            }

            toBet(plan, unit);
            btnSubmit.setFocusPainted(false);
        });

        // 开启关闭自动下注
        btnAuto.addActionListener(actionEvent -> {
            String text = btnAuto.getText().trim();
            if (text.equals("自动")) {
                autoBet = true;
                btnAuto.setText("停止");

                tfPlan.setEditable(false);
                tfUnit.setEditable(false);

                int sum = 5 * Integer.valueOf(tfUnit.getText().trim());
                lbSumMoney.setText(String.valueOf(sum));
                btnSubmit.setEnabled(false);
            } else {
                autoBet = false;
                btnAuto.setText("自动");

                tfPlan.setEditable(true);
                tfUnit.setEditable(true);
                lbSumMoney.setText("0");
                btnSubmit.setEnabled(true);
            }
        });
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lbUsername = new javax.swing.JLabel();
        lbUser = new javax.swing.JLabel();
        lbBalance = new javax.swing.JLabel();
        lbMoney = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        lbToday = new javax.swing.JLabel();
        lbTodayWin = new javax.swing.JLabel();
        lbRefresh = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        lbCurrentPeriod = new javax.swing.JLabel();
        lbLastPeriod = new javax.swing.JLabel();
        lbLastResult = new javax.swing.JLabel();
        lbPeriod = new javax.swing.JLabel();
        tfCurrentBet = new javax.swing.JTextField();
        lbCurrentSum = new javax.swing.JLabel();
        lbStatus = new javax.swing.JLabel();
        lbPlan = new javax.swing.JLabel();
        tfPlan = new javax.swing.JTextField();
        lbUnit = new javax.swing.JLabel();
        tfUnit = new javax.swing.JTextField();
        lbSum = new javax.swing.JLabel();
        lbSumMoney = new javax.swing.JLabel();
        btnSubmit = new javax.swing.JButton();
        btnAuto = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("彩之家");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setToolTipText("");

        lbUsername.setText("账号：");

        lbUser.setText("diyiliu");

        lbBalance.setText("余额：");

        lbMoney.setText("188.05");

        btnLogout.setText("注销");
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        lbToday.setText("今日已结：");

        lbTodayWin.setText("100.00");

        //lbRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/synchronize.png"))); // NOI18N
        lbRefresh.setText(" ");
        lbRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbUsername)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbUser)
                                .addGap(18, 18, 18)
                                .addComponent(lbBalance)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbMoney)
                                .addGap(18, 18, 18)
                                .addComponent(lbToday)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbTodayWin)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLogout)
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbUsername)
                                        .addComponent(lbUser)
                                        .addComponent(lbBalance)
                                        .addComponent(lbMoney)
                                        .addComponent(btnLogout)
                                        .addComponent(lbToday)
                                        .addComponent(lbTodayWin)
                                        .addComponent(lbRefresh))
                                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "排名1-10", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 0, 12), new java.awt.Color(0, 0, 255))); // NOI18N

        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        lbCurrentPeriod.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        lbCurrentPeriod.setForeground(new java.awt.Color(0, 153, 102));
        lbCurrentPeriod.setText("20180228118");

        lbLastPeriod.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        lbLastPeriod.setText("20180228107");

        lbLastResult.setFont(new java.awt.Font("宋体", 1, 12)); // NOI18N
        lbLastResult.setForeground(new java.awt.Color(204, 0, 0));
        lbLastResult.setText("中");

        lbPeriod.setFont(new java.awt.Font("宋体", 1, 12)); // NOI18N
        lbPeriod.setText("期");

        tfCurrentBet.setEditable(false);
        tfCurrentBet.setBackground(new java.awt.Color(255, 255, 255));
        tfCurrentBet.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        tfCurrentBet.setForeground(new java.awt.Color(102, 102, 102));
        tfCurrentBet.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        tfCurrentBet.setText("01 02 03 04 05 07 08 09 10");
        tfCurrentBet.setAutoscrolls(false);
        tfCurrentBet.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 1));
        tfCurrentBet.setPreferredSize(new java.awt.Dimension(165, 21));
        tfCurrentBet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfCurrentBetActionPerformed(evt);
            }
        });

        lbCurrentSum.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        lbCurrentSum.setForeground(new java.awt.Color(204, 0, 51));
        lbCurrentSum.setText("25");

        lbStatus.setFont(new java.awt.Font("宋体", 1, 12)); // NOI18N
        lbStatus.setForeground(new java.awt.Color(0, 153, 102));
        lbStatus.setText("封盘");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(lbCurrentPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lbPeriod)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lbCurrentSum))
                                        .addComponent(tfCurrentBet, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addComponent(lbStatus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
                                .addComponent(lbLastPeriod)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbLastResult)
                                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbCurrentPeriod)
                                        .addComponent(lbLastPeriod)
                                        .addComponent(lbLastResult)
                                        .addComponent(lbPeriod)
                                        .addComponent(lbCurrentSum)
                                        .addComponent(lbStatus))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                .addComponent(tfCurrentBet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        lbPlan.setText("计划追号：");

        tfPlan.setText("01 02 03 04 05");

        lbUnit.setText("单注金额：");

        tfUnit.setText("5");
        tfUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfUnitActionPerformed(evt);
            }
        });

        lbSum.setText("下注金额：");

        lbSumMoney.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        lbSumMoney.setForeground(new java.awt.Color(204, 0, 0));
        lbSumMoney.setText("25");

        btnSubmit.setText("确定");
        btnSubmit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnAuto.setText("自动");
        btnAuto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbPlan)
                                        .addComponent(lbUnit)
                                        .addComponent(lbSum))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(lbSumMoney)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnAuto))
                                        .addComponent(tfPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tfUnit))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSubmit)
                                .addContainerGap())
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[]{tfPlan, tfUnit});

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[]{lbPlan, lbSum, lbUnit});

        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lbPlan)
                                        .addComponent(tfPlan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbUnit)
                                        .addComponent(tfUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbSum)
                                        .addComponent(lbSumMoney)
                                        .addComponent(btnAuto)
                                        .addComponent(btnSubmit))
                                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("幸运飞艇", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTabbedPane1)
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTabbedPane1)
                                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void tfUnitActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void tfCurrentBetActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void tfAutoUnitActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
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

        UIHelper.beautify(Constant.LOOK_STYLE);

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainUI().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify
    private javax.swing.JButton btnAuto;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbBalance;
    private javax.swing.JLabel lbCurrentPeriod;
    private javax.swing.JLabel lbCurrentSum;
    private javax.swing.JLabel lbLastPeriod;
    private javax.swing.JLabel lbLastResult;
    private javax.swing.JLabel lbMoney;
    private javax.swing.JLabel lbPeriod;
    private javax.swing.JLabel lbPlan;
    private javax.swing.JLabel lbRefresh;
    private javax.swing.JLabel lbStatus;
    private javax.swing.JLabel lbSum;
    private javax.swing.JLabel lbSumMoney;
    private javax.swing.JLabel lbToday;
    private javax.swing.JLabel lbTodayWin;
    private javax.swing.JLabel lbUnit;
    private javax.swing.JLabel lbUser;
    private javax.swing.JLabel lbUsername;
    private javax.swing.JTextField tfCurrentBet;
    private javax.swing.JTextField tfPlan;
    private javax.swing.JTextField tfUnit;
    // End of variables declaration

    public void setCurrentUser(String username) {
        lbUser.setText(username);
    }

    public void setBalance() {
        java.awt.EventQueue.invokeLater(() -> {
            String balance = webContainer.getBalance();

            lbMoney.setText(balance);
            tfCurrentBet.grabFocus();
            // 获取sessionId
            webContainer.getPlayHoldem();
        });
    }

    public void setTodayWin() {
        java.awt.EventQueue.invokeLater(() -> {
            String today = sumToday();
            if (!today.equals("0")) {
                lbTodayWin.setText(today);
            }
        });
    }


    public void refresh(Map dataMap) {
        lbMoney.setText((String) dataMap.get("balance"));

        Map detail = (Map) dataMap.get("XYFT");
        String gameNo = (String) detail.get("gameNo");
        String lastGameNo = (String) detail.get("lastGameNo");
        List lastGameResult = (List) detail.get("lastGameResult");
        int first = (int) lastGameResult.get(0);

        lbCurrentPeriod.setText(gameNo);
        lbLastPeriod.setText(lastGameNo);

        if (betCacheProvider.containsKey(gameNo)) {
            BetRecord record = (BetRecord) betCacheProvider.get(gameNo);
            lbCurrentSum.setText(String.valueOf(record.getMoney()));
            tfCurrentBet.setText(record.getPlan());
        } else {
            lbCurrentSum.setText("0");
            tfCurrentBet.setText("");
        }

        if (betCacheProvider.containsKey(lastGameNo)) {
            BetRecord record = (BetRecord) betCacheProvider.get(lastGameNo);
            if (record.getResult() == 0) {
                int result = -1;
                List<Integer> nos = record.getPlanNos();
                for (Integer no : nos) {
                    if (no == first) {
                        result = 1;
                        break;
                    }
                }

                record.setResult(result);
                if (result == 1) {
                    lbLastResult.setText("中");
                } else {
                    lbLastResult.setText("挂");
                }

                // 统计今日输赢
                String today = sumToday();
                if (!today.equals("0")) {
                    lbTodayWin.setText(today);
                }

                // 自动下注
                if (autoBet) {
                    toAutoBet();
                }
            }
        } else {
            lbLastResult.setText("无");
        }

        int gameTime = (int) detail.get("gameTime");
        int closeTime = (int) detail.get("closeTime");
        int gap = gameTime - closeTime;

        String status = (String) detail.get("gameStatus");
        if ("BETTING".equals(status)) {
            if (gap > 30) {
                lbStatus.setText("下注");
                if (autoBet && !betCacheProvider.containsKey(gameNo)) {

                    toAutoBet();
                }
            } else if (gap < 3) {
                lbStatus.setText("封盘");
            } else {
                lbStatus.setText("临界");
            }
        } else {
            lbStatus.setText("关盘");
            if (autoBet) {
                autoBet = false;
            }
        }

        //logger.info("更新数据...");
    }

    public void calcMoney() {
        String plan = tfPlan.getText().trim();
        String unit = tfUnit.getText().trim();
        if (StringUtils.isEmpty(plan) || StringUtils.isEmpty(unit)) {

            return;
        }

        String[] noArr = plan.split(" ");
        int sum = noArr.length * Integer.parseInt(unit);
        lbSumMoney.setText(String.valueOf(sum));
    }

    /**
     * 自动下注
     */
    public void toAutoBet() {
        try {
            String winPlan = helpWinUtil.getPlan(lotteryType);
            if (StringUtils.isEmpty(winPlan)) {
                logger.error("自动下注失败，无法获取助赢计划!");
                return;
            }
            tfPlan.setText(winPlan);

            String plan = tfPlan.getText().trim();
            String unit = tfUnit.getText().trim();

            toBet(plan, unit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下注
     */
    public synchronized void toBet(String plan, String unit) {
        String gameNo = lbCurrentPeriod.getText().trim();
        if (betCacheProvider.containsKey(gameNo)){
            logger.info("[{}]期已经下注, 取消本次提交...", gameNo);
            return;
        }

        int balance = Integer.parseInt(lbMoney.getText().trim());
        int total = 5 * Integer.valueOf(unit);
        if (balance > total){
        }else {
            logger.warn("余额不足，无法下注...");
            if (autoBet){
                autoBet = false;
            }

            return;
        }

        Map map = (Map) agentCacheProvider.get("XYFT");
        Map oddMap = (Map) map.get("odds");

        Map odd = (Map) oddMap.get("BALL_1");
        String[] noArr = plan.split(" ");

        List<Integer> nos = new ArrayList();
        List bets = new ArrayList();
        String ball = "BALL_1";
        for (String no : noArr) {
            List l = new ArrayList();
            int i = Integer.parseInt(no);
            String n = "NO_" + i;
            l.add(n);
            l.add(ball);
            l.add(unit);
            l.add(odd.get(n));

            bets.add(l);
            // 记录
            nos.add(i);
        }

        BetReturn betReturn = webContainer.submitBet(bets);
        if (betReturn.isSuccess()) {
            tfPlan.setText("");
            lbSumMoney.setText("0");

            int sum = noArr.length * Integer.parseInt(unit);
            gameNo = betReturn.getPeriod();

            BetRecord record = new BetRecord();
            record.setPeriod(gameNo);
            record.setPlan(plan);
            record.setPlanNos(nos);
            record.setUnit(Integer.parseInt(unit));
            record.setMoney(sum);
            record.setDetail(bets);
            record.setDatetime(System.currentTimeMillis());

            logger.info("下注成功[{}]...", gameNo);
            betCacheProvider.put(gameNo, record);
        }
    }

    private String sumToday() {
        List<BetDetail> details = webContainer.queryReportDetail();
        if (details == null || details.size() < 1) {

            return "0";
        }

        BigDecimal sum = new BigDecimal(0);
        for (BetDetail detail : details) {
            sum = sum.add(detail.getWinLoss());
        }

        return String.format("%.2f", sum.doubleValue());
    }
}