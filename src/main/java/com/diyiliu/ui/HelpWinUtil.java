package com.diyiliu.ui;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;

/**
 * Description: HelpWinUtil
 * Author: DIYILIU
 * Update: 2018-03-09 23:54
 */
public class HelpWinUtil {
   private final User32 user = User32.INSTANCE;

   public String getPlan(String winName) throws Exception{
       String plan = null;

       WinDef.HWND hwnd = user.FindWindow(null, winName);
       if (hwnd != null) {
           user.ShowWindow(hwnd, 9);
           User32.INSTANCE.SetForegroundWindow(hwnd);

           WinDef.RECT planRect = new WinDef.RECT();
           User32.INSTANCE.GetWindowRect(hwnd, planRect);

           int width = planRect.right - planRect.left;
           int height = planRect.bottom - planRect.top;

           User32.INSTANCE.MoveWindow(hwnd, 0, 0, width, height, true);

           int x = width - 55;
           int y = 230;

           // 移动鼠标复制
           Robot myRobot = new Robot();
           myRobot.mouseMove(x, y);
           myRobot.setAutoDelay(200);
           myRobot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
           myRobot.setAutoDelay(200);
           myRobot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);

           // 移开
           myRobot.setAutoDelay(50);
           myRobot.mouseMove(x, y - 40);

           // 获取剪切板中的内容
           Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
           Transferable clipTf = sysClip.getContents(null);
           if (clipTf != null) {
               // 检查内容是否是文本类型
               if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                   plan = (String) clipTf.getTransferData(DataFlavor.stringFlavor);

                   // 清除剪切板内容
                   sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
                   clipTf = new StringSelection("");
                   sysClip.setContents(clipTf, null);
               }
           }
       }

       return plan;
   }
}
