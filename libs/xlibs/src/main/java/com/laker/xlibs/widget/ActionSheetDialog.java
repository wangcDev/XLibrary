package com.laker.xlibs.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnDismissListener;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.laker.xlibs.R;
import java.util.ArrayList;
import java.util.List;


public class ActionSheetDialog {
    private Context context;
    private boolean isCustomView;
    private Dialog dialog;
    private TextView txt_title;
    private TextView txt_cancel;
    private LinearLayout lLayout_content;
    private RelativeLayout layout_content;
    private ScrollView sLayout_content;
    private boolean showTitle = false;
    private List<SheetItem> sheetItemList;
    private Display display;
    OnSheetItemClickListener listener = null;
    OnSheetItemRightClickListener listenerRight = null;

    public ActionSheetDialog OnSheetItemClickListener(OnSheetItemClickListener listener) {
        this.listener = listener;
        return this;
    }
    public ActionSheetDialog OnSheetItemRightClickListener(OnSheetItemRightClickListener listenerRight) {
        this.listenerRight = listenerRight;
        return this;
    }

    public ActionSheetDialog(Context context) {
//        this.context = context;
//        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        display = windowManager.getDefaultDisplay();
        this(context,false);
    }

    public ActionSheetDialog(Context context,boolean isCustomView) {
        this.context = context;
        this.isCustomView = isCustomView;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public ActionSheetDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.view_actionsheet, null);

        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth(display.getWidth());

        // 获取自定义Dialog布局中的控件
        sLayout_content = (ScrollView) view.findViewById(R.id.sLayout_content);
        lLayout_content = (LinearLayout) view.findViewById(R.id.lLayout_content);
        layout_content = (RelativeLayout) view.findViewById(R.id.layout_content);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_cancel = (TextView) view.findViewById(R.id.txt_cancel);
        txt_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);

        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);

        return this;
    }

    public ActionSheetDialog setTitle(String title) {
        showTitle = true;
        txt_title.setVisibility(View.VISIBLE);
        txt_title.setText(title);
        return this;
    }

    public ActionSheetDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public ActionSheetDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    //    /**
//     * @param strItem  条目名称
//     * @param color    条目字体颜色，设置null则默认蓝色
//     * @param listener
//     * @return
//     */
//    public ActionSheetDialog addSheetItem(String strItem, int color, OnSheetItemClickListener listener) {
//        if (sheetItemList == null) {
//            sheetItemList = new ArrayList<SheetItem>();
//        }
//        sheetItemList.add(new SheetItem(strItem, color, listener));
//        return this;
//    }
    public ActionSheetDialog addSheetItems(List<SheetItem> items) {
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<SheetItem>();
        }
        sheetItemList.addAll(items);
        setSheetItems();
        return this;
    }

    public ActionSheetDialog setView(View view, boolean showCancel) {
        layout_content.addView(view);
        if (!showCancel)
            txt_cancel.setVisibility(View.GONE);
        return this;
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public ActionSheetDialog setOnDismiss(OnDismissListener listener) {
        dialog.setOnDismissListener(listener);
        return this;
    }


    /**
     * 设置条目布局
     */
    private void setSheetItems() {

        if (sheetItemList == null || sheetItemList.size() <= 0) {
            return;
        }

        int size = sheetItemList.size();

        // 添加条目过多的时候控制高度
        if (size >= 7) {
            LayoutParams params = (LayoutParams) sLayout_content.getLayoutParams();
            params.height = display.getHeight() / 2;
            sLayout_content.setLayoutParams(params);
        }

        if (isCustomView){
            // 循环添加条目
            for (int i = 0; i < size; i++) {
                View actionSheetItem = LayoutInflater.from(context).inflate(R.layout.view_actionsheet_custom_item, null);
                final int index = i;
                SheetItem sheetItem = sheetItemList.get(i);
                String strItem = sheetItem.name;

                if (sheetItem.itemClickListener != null) {
                    listener = (OnSheetItemClickListener) sheetItem.itemClickListener;
                }

                actionSheetItem.findViewById(R.id.iv_right).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listenerRight != null) {
                            listenerRight.onClick(index);
                        }
                        dialog.dismiss();
                    }
                });

                TextView textView = (TextView) actionSheetItem.findViewById(R.id.tv_center);
                textView.setText(strItem);
                textView.setTextSize(18);
                textView.setGravity(Gravity.CENTER);

                // 背景图片
                if (size == 1) {
                    if (showTitle) {
                        actionSheetItem.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    } else {
                        actionSheetItem.setBackgroundResource(R.drawable.actionsheet_single_selector);
                    }
                } else {
                    if (showTitle) {
                        if (i >= 1 && i < size) {
                            actionSheetItem.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                        } else {
                            actionSheetItem.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                        }
                    } else {
                        if (i == 0) {
                            actionSheetItem.setBackgroundResource(R.drawable.actionsheet_top_selector);
                        } else if (i < size - 1) {
                            actionSheetItem.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                        } else {
                            actionSheetItem.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                        }
                    }
                }

                // 字体颜色
                if (sheetItem.color == 0) {
                    textView.setTextColor(0xff037BFF);
                } else {
                    textView.setTextColor(sheetItem.color);
                }

                // 高度
                float scale = context.getResources().getDisplayMetrics().density;
                int height = (int) (45 * scale + 0.5f);
                actionSheetItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height));

                // 点击事件
                actionSheetItem.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onClick(index);
                        }
                        dialog.dismiss();
                    }
                });
                lLayout_content.addView(actionSheetItem);

            }
        }else {
            // 循环添加条目
            for (int i = 0; i < size; i++) {
                final int index = i;
                SheetItem sheetItem = sheetItemList.get(i);
                String strItem = sheetItem.name;

                if (sheetItem.itemClickListener != null) {
                    listener = (OnSheetItemClickListener) sheetItem.itemClickListener;
                }

                TextView textView = new TextView(context);
                textView.setText(strItem);
                textView.setTextSize(18);
                textView.setGravity(Gravity.CENTER);

                // 背景图片
                if (size == 1) {
                    if (showTitle) {
                        textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                    } else {
                        textView.setBackgroundResource(R.drawable.actionsheet_single_selector);
                    }
                } else {
                    if (showTitle) {
                        if (i >= 1 && i < size) {
                            textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                        } else {
                            textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                        }
                    } else {
                        if (i == 0) {
                            textView.setBackgroundResource(R.drawable.actionsheet_top_selector);
                        } else if (i < size - 1) {
                            textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
                        } else {
                            textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
                        }
                    }
                }

                // 字体颜色
                if (sheetItem.color == 0) {
                    textView.setTextColor(0xff037BFF);
                } else {
                    textView.setTextColor(sheetItem.color);
                }

                // 高度
                float scale = context.getResources().getDisplayMetrics().density;
                int height = (int) (45 * scale + 0.5f);
                textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height));

                // 点击事件
                textView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onClick(index);
                        }
                        dialog.dismiss();
                    }
                });

                lLayout_content.addView(textView);

            }
        }

    }
//
//    /**
//     * 设置条目布局
//     */
//    private void setSheetItems() {
//
//        if (sheetItemList == null || sheetItemList.size() <= 0) {
//            return;
//        }
//
//        int size = sheetItemList.size();
//
//        // 添加条目过多的时候控制高度
//        if (size >= 7) {
//            LayoutParams params = (LayoutParams) sLayout_content.getLayoutParams();
//            params.height = display.getHeight() / 2;
//            sLayout_content.setLayoutParams(params);
//        }
//
//        if (isCustomView){
//            // 循环添加条目
//            for (int i = 0; i < size; i++) {
//                View actionSheetItem = LayoutInflater.from(context).inflate(R.layout.view_actionsheet_custom_item, null);
//                final int index = i;
//                SheetItem sheetItem = sheetItemList.get(i);
//                String strItem = sheetItem.name;
//
//                if (sheetItem.itemClickListener != null) {
//                    listener = (OnSheetItemClickListener) sheetItem.itemClickListener;
//                }
//
//                 actionSheetItem.findViewById(R.id.iv_right).setOnClickListener(new OnClickListener() {
//                     @Override
//                     public void onClick(View view) {
//                         if (listenerRight != null) {
//                             listenerRight.onClick(index);
//                         }
//                         dialog.dismiss();
//                     }
//                 });
//
//                TextView textView = (TextView) actionSheetItem.findViewById(R.id.tv_center);
//                textView.setText(strItem);
//                textView.setTextSize(18);
//                textView.setGravity(Gravity.CENTER);
//
//                // 背景图片
//                if (size == 1) {
//                    if (showTitle) {
//                        actionSheetItem.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
//                    } else {
//                        actionSheetItem.setBackgroundResource(R.drawable.actionsheet_single_selector);
//                    }
//                } else {
//                    if (showTitle) {
//                        if (i >= 1 && i < size) {
//                            actionSheetItem.setBackgroundResource(R.drawable.actionsheet_middle_selector);
//                        } else {
//                            actionSheetItem.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
//                        }
//                    } else {
//                        if (i == 0) {
//                            actionSheetItem.setBackgroundResource(R.drawable.actionsheet_top_selector);
//                        } else if (i < size - 1) {
//                            actionSheetItem.setBackgroundResource(R.drawable.actionsheet_middle_selector);
//                        } else {
//                            actionSheetItem.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
//                        }
//                    }
//                }
//
//                // 字体颜色
//                if (sheetItem.color == 0) {
//                    textView.setTextColor(0xff037BFF);
//                } else {
//                    textView.setTextColor(sheetItem.color);
//                }
//
//                // 高度
//                float scale = context.getResources().getDisplayMetrics().density;
//                int height = (int) (45 * scale + 0.5f);
//                actionSheetItem.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height));
//
//                // 点击事件
//                actionSheetItem.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (listener != null) {
//                            listener.onClick(index);
//                        }
//                        dialog.dismiss();
//                    }
//                });
//                lLayout_content.addView(actionSheetItem);
//
//            }
//        }else {
//           // 循环添加条目
//            for (int i = 0; i < size; i++) {
//                final int index = i;
//                SheetItem sheetItem = sheetItemList.get(i);
//                String strItem = sheetItem.name;
//
//                if (sheetItem.itemClickListener != null) {
//                    listener = (OnSheetItemClickListener) sheetItem.itemClickListener;
//                }
//
//                TextView textView = new TextView(context);
//                textView.setText(strItem);
//                textView.setTextSize(18);
//                textView.setGravity(Gravity.CENTER);
//
//                // 背景图片
//                if (size == 1) {
//                    if (showTitle) {
//                        textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
//                    } else {
//                        textView.setBackgroundResource(R.drawable.actionsheet_single_selector);
//                    }
//                } else {
//                    if (showTitle) {
//                        if (i >= 1 && i < size) {
//                            textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
//                        } else {
//                            textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
//                        }
//                    } else {
//                        if (i == 0) {
//                            textView.setBackgroundResource(R.drawable.actionsheet_top_selector);
//                        } else if (i < size - 1) {
//                            textView.setBackgroundResource(R.drawable.actionsheet_middle_selector);
//                        } else {
//                            textView.setBackgroundResource(R.drawable.actionsheet_bottom_selector);
//                        }
//                    }
//                }
//
//                // 字体颜色
//                if (sheetItem.color == 0) {
//                    textView.setTextColor(0xff037BFF);
//                } else {
//                    textView.setTextColor(sheetItem.color);
//                }
//
//                // 高度
//                float scale = context.getResources().getDisplayMetrics().density;
//                int height = (int) (45 * scale + 0.5f);
//                textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height));
//
//                // 点击事件
//                textView.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (listener != null) {
//                            listener.onClick(index);
//                        }
//                        dialog.dismiss();
//                    }
//                });
//
//                lLayout_content.addView(textView);
//
//            }
//        }
//
//    }

    public void show() {
        dialog.show();
    }

    public interface OnSheetItemClickListener {
        void onClick(int which);
    }
    public interface OnSheetItemRightClickListener {
        void onClick(int which);
    }

    public static class SheetItem {
        public String name;
        public int id;
        OnSheetItemClickListener itemClickListener;
        int color;

        public SheetItem(String name) {
            this.name = name;
            this.color = 0;
            this.id = 0;
        }
        public SheetItem(String name,int id) {
            this.name = name;
            this.color = 0;
            this.id = id;
        }

        public SheetItem(String name, int color, OnSheetItemClickListener itemClickListener) {
            this.name = name;
            this.color = color;
            this.itemClickListener = itemClickListener;
        }
    }

}
