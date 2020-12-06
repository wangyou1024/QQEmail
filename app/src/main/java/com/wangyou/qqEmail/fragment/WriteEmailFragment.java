package com.wangyou.qqEmail.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wangyou.qqEmail.R;
import com.wangyou.qqEmail.entity.Email;


/**
 * 参考：https://www.jb51.net/article/185144.htm
 */
public class WriteEmailFragment extends BottomSheetDialogFragment {

    private static Email email;

    private EditText etReceivePerson;
    private EditText etSender;
    private EditText etTheme;
    private EditText etContent;

    private TextView tvCancel;
    private TextView tvSend;

    static {
        email = new Email();
    }

    public WriteEmailFragment() {
        // Required empty public constructor
    }

    public static WriteEmailFragment newInstance() {
        WriteEmailFragment fragment = new WriteEmailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.i(this.getClass().getSimpleName(), "onStart: start");
        super.onStart();
        //获取dialog对象
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        //把windowsd的默认背景颜色去掉，不然圆角显示不见
        dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackground(new ColorDrawable(Color.TRANSPARENT));
        //获取diglog的根部局
        FrameLayout bottomSheet = dialog.getDelegate().findViewById(R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            //获取根部局的LayoutParams对象
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
            layoutParams.height = getPeekHeight();
            //修改弹窗的最大高度，不允许上滑（默认可以上滑）
            bottomSheet.setLayoutParams(layoutParams);

            final BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
            //peekHeight即弹窗的最大高度
            behavior.setPeekHeight(getPeekHeight());
            // 初始为展开状态
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            //设置监听
            tvCancel.setOnClickListener(view -> {
                Log.i("tvCancel","onClick");
                //关闭弹窗
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            });
            tvSend.setOnClickListener((view)->{
                Log.i("tvSend","onClick");
                Toast.makeText(getContext(),"你发送了一封邮件给"+email.getReceivePerson(),Toast.LENGTH_SHORT).show();
                email = new Email();
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            });
        }
    }

    /**
     * 弹窗高度，默认为屏幕高度减10
     * 子类可重写该方法返回peekHeight
     *
     * @return height
     */
    protected int getPeekHeight() {
        int peekHeight = getResources().getDisplayMetrics().heightPixels;
        //设置弹窗高度为屏幕高度的3/4
        return peekHeight - 10;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_write_email, container, false);
        initView(view);
        initData();
        return view;
    }

    public void initView(View view) {
        Log.i(this.getClass().getSimpleName(),"initView start");
        etReceivePerson = view.findViewById(R.id.et_receive_person);
        // 监听变化
        etReceivePerson.addTextChangedListener(new MyTextWatcher(email,0));
        // 切换焦点
        etReceivePerson.setOnEditorActionListener(((v, actionId, event) -> {
            Log.i("etReceivePerson","onEditorAction");
            etReceivePerson.clearFocus();
            etSender.requestFocus();
            return true;
        }));
        etSender = view.findViewById(R.id.et_sender);
        etSender.addTextChangedListener(new MyTextWatcher(email,1));
        etSender.setOnEditorActionListener(((v, actionId, event) -> {
            Log.i("etSender","onEditorAction");
            etSender.clearFocus();
            etTheme.requestFocus();
            return true;
        }));
        etTheme = view.findViewById(R.id.et_theme);
        etTheme.addTextChangedListener(new MyTextWatcher(email,2));
        etTheme.setOnEditorActionListener(((v, actionId, event) -> {
            Log.i("etTheme","onEditorAction");
            etTheme.clearFocus();
            etContent.requestFocus();
            return true;
        }));
        etContent = view.findViewById(R.id.et_content);
        etContent.addTextChangedListener(new MyTextWatcher(email,3));
        tvCancel = view.findViewById(R.id.tv_cancel);
        tvSend = view.findViewById(R.id.tv_send);
        Log.i(this.getClass().getSimpleName(),"initView end");
    }

    public void initData() {
        Log.i(this.getClass().getSimpleName(),"initData start");
        etReceivePerson.setText(email.getReceivePerson());
        etSender.setText(email.getSender());
        etTheme.setText(email.getTheme());
        etContent.setText(email.getContent());
        Log.i(this.getClass().getSimpleName(),"initData end");
    }

    private class MyTextWatcher implements TextWatcher{

        private Email email;
        private Integer type;

        public MyTextWatcher(Email email, Integer type) {
            this.email = email;
            this.type = type;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (type){
                case 0: email.setReceivePerson(s.toString());break;
                case 1: email.setSender(s.toString());break;
                case 2: email.setTheme(s.toString());break;
                case 3: email.setContent(s.toString());break;
                default:break;
            }
        }
    }
}