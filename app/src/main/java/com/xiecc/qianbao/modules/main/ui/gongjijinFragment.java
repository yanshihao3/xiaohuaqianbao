package com.xiecc.qianbao.modules.main.ui;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.xiecc.qianbao.R;

import java.math.BigDecimal;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class gongjijinFragment extends Fragment {


    @Bind(R.id.zevj1)
    EditText zevj1;
    @Bind(R.id.nxvj1)
    Spinner nxvj1;
    @Bind(R.id.lvvvj1)
    EditText lvvvj1;
    @Bind(R.id.subj1)
    Button subj1;

    @Bind(R.id.am10)
    TextView am10;
    @Bind(R.id.am20)
    TextView am20;
    @Bind(R.id.am11)
    TextView am11;
    @Bind(R.id.am21)
    TextView am21;
    @Bind(R.id.am12)
    TextView am12;
    @Bind(R.id.am22)
    TextView am22;
    @Bind(R.id.am13)
    TextView am13;
    @Bind(R.id.am23)
    TextView am23;
    @Bind(R.id.am14)
    TextView am14;
    @Bind(R.id.am24)
    TextView am24;

    private static final String[] nx = {"1", "2", "3", "4", "5", "10", "15",
            "20", "25", "30"};
    private static final String[] lv = {"最新基准利率7折", "最新基准利率7.5折", "最新基准利率8折",
            "最新基准利率8.5折", "最新基准利率9折", "最新基准利率9.5折", "最新基准利率", "最新基准利率1.1倍",
            "最新基准利率1.2倍", "最新基准利率1.3倍"};


    public gongjijinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gongjijin, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, nx);
        // 设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 将adapter 添加到spinner中
        nxvj1.setAdapter(adapter);
        nxvj1.setSelection(7, true);
        // 设置默认值
        nxvj1.setVisibility(View.VISIBLE);
        // 添加事件Spinner事件监听
        nxvj1.setOnItemSelectedListener(new SpinnerSelectedListener1(lvvvj1));

        this.subj1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Editable value = zevj1.getText();
                if (value.toString() == null || value.toString().length() <= 0) {
                    new AlertDialog.Builder(getActivity()).setTitle("提示")
                            .setMessage("贷款总额不能为空")
                            .setPositiveButton("OK", null).show();
                    return;
                }
                Editable value1 = lvvvj1.getText();
                if (value1.toString() == null
                        || value1.toString().length() <= 0) {
                    new AlertDialog.Builder(getActivity()).setTitle("提示")
                            .setMessage("利率不能为空").setPositiveButton("OK", null)
                            .show();
                    return;
                }

                double ze = Double.parseDouble(value.toString());
                double nx = Double.parseDouble(nxvj1.getSelectedItem()
                        .toString()) * 12;
                double rate = Double.parseDouble(lvvvj1.getText().toString()) / 100;

                cal(ze, nx, rate);

            }

        });
    }

    class SpinnerSelectedListener1 implements AdapterView.OnItemSelectedListener {

        EditText et;

        public SpinnerSelectedListener1(EditText et) {
            this.et = et;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            if (position <= 4)
                et.setText(String.format("%.02f", 4.0));
            else
                et.setText(String.format("%.02f", 4.5));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }

    }
    public void cal(double ze, double nx, double rate) {
        double zem = (ze * rate / 12 * Math.pow((1 + rate / 12), nx))
                / (Math.pow((1 + rate / 12), nx) - 1);
        double amount = zem * nx;
        double rateAmount = amount - ze;

        BigDecimal zemvalue = new BigDecimal(zem);
        double zemval = zemvalue.setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();

        BigDecimal amountvalue = new BigDecimal(amount);
        double amountval = amountvalue.setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();

        BigDecimal rateAmountvalue = new BigDecimal(rateAmount);
        double rateAmountval = rateAmountvalue.setScale(2,
                BigDecimal.ROUND_HALF_UP).doubleValue();

        double benjinm = ze / nx;
        double lixim = ze * (rate / 12);
        double diff = benjinm * (rate / 12);
        double huankuanm = benjinm + lixim;
        double zuihoukuan = diff + benjinm;
        double av = (huankuanm + zuihoukuan) / 2;
        double zong = av * nx;
        double zongli = zong - ze;

        BigDecimal huankuanmvalue = new BigDecimal(huankuanm);
        double huankuanmval = huankuanmvalue.setScale(2,
                BigDecimal.ROUND_HALF_UP).doubleValue();

        BigDecimal diffvalue = new BigDecimal(diff);
        double diffmval = diffvalue.setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();

        BigDecimal zongvalue = new BigDecimal(zong);
        double zongval = zongvalue.setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();

        BigDecimal zonglivalue = new BigDecimal(zongli);
        double zonglival = zonglivalue.setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();

        am10.setText(ze + "元");
        am20.setText(ze + "元");

        am11.setText(nx + "月");
        am21.setText(nx + "月");

        am12.setText(zemval + "元");
        am22.setText("首月" + huankuanmval + ",月减" + diffmval);

        am13.setText(rateAmountval + "元");
        am23.setText(zonglival + "元");

        am14.setText(amountval + "元");
        am24.setText(zongval + "元");
    }

    public void cal(double ze, double nx, double rate, double ze1, double rate1) {
        double zem = (ze * rate / 12 * Math.pow((1 + rate / 12), nx))
                / (Math.pow((1 + rate / 12), nx) - 1);
        double amount = zem * nx;
        double rateAmount = amount - ze;

        double zem1 = (ze1 * rate1 / 12 * Math.pow((1 + rate1 / 12), nx))
                / (Math.pow((1 + rate1 / 12), nx) - 1);
        double amount1 = zem1 * nx;
        double rateAmount1 = amount1 - ze1;

        BigDecimal zemvalue = new BigDecimal(zem + zem1);
        double zemval = zemvalue.setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();

        BigDecimal amountvalue = new BigDecimal(amount + amount1);
        double amountval = amountvalue.setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();

        BigDecimal rateAmountvalue = new BigDecimal(rateAmount + rateAmount1);
        double rateAmountval = rateAmountvalue.setScale(2,
                BigDecimal.ROUND_HALF_UP).doubleValue();

        double benjinm = ze / nx;
        double lixim = ze * (rate / 12);
        double diff = benjinm * (rate / 12);
        double huankuanm = benjinm + lixim;
        double zuihoukuan = diff + benjinm;
        double av = (huankuanm + zuihoukuan) / 2;
        double zong = av * nx;
        double zongli = zong - ze;

        double benjinm1 = ze1 / nx;
        double lixim1 = ze1 * (rate1 / 12);
        double diff1 = benjinm1 * (rate1 / 12);
        double huankuanm1 = benjinm1 + lixim1;
        double zuihoukuan1 = diff1 + benjinm1;
        double av1 = (huankuanm1 + zuihoukuan1) / 2;
        double zong1 = av1 * nx;
        double zongli1 = zong1 - ze1;

        BigDecimal huankuanmvalue = new BigDecimal(huankuanm + huankuanm1);
        double huankuanmval = huankuanmvalue.setScale(2,
                BigDecimal.ROUND_HALF_UP).doubleValue();

        BigDecimal diffvalue = new BigDecimal(diff + diff1);
        double diffmval = diffvalue.setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();

        BigDecimal zongvalue = new BigDecimal(zong + zong1);
        double zongval = zongvalue.setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();

        BigDecimal zonglivalue = new BigDecimal(zongli + zongli1);
        double zonglival = zonglivalue.setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();

        am10.setText((ze + ze1) + "元");
        am20.setText((ze + ze1) + "元");

        am11.setText(nx + "月");
        am21.setText(nx + "月");

        am12.setText(zemval + "元");
        am22.setText("首月" + huankuanmval + ",月减" + diffmval);

        am13.setText(rateAmountval + "元");
        am23.setText(zonglival + "元");

        am14.setText(amountval + "元");
        am24.setText(zongval + "元");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.subj1, R.id.cancelj1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.subj1:
                Editable value = zevj1.getText();
                if (value.toString() == null || value.toString().length() <= 0) {
                    new AlertDialog.Builder(getActivity()).setTitle("提示")
                            .setMessage("贷款总额不能为空")
                            .setPositiveButton("OK", null).show();
                    return;
                }
                Editable value1 = lvvvj1.getText();
                if (value1.toString() == null
                        || value1.toString().length() <= 0) {
                    new AlertDialog.Builder(getActivity()).setTitle("提示")
                            .setMessage("利率不能为空").setPositiveButton("OK", null)
                            .show();
                    return;
                }

                double ze = Double.parseDouble(value.toString());
                double nx = Double.parseDouble(nxvj1.getSelectedItem()
                        .toString()) * 12;
                double rate = Double.parseDouble(lvvvj1.getText().toString()) / 100;

                cal(ze, nx, rate);

                break;
            case R.id.cancelj1:
                     zevj1.setText("");
                     nxvj1.setSelection(7, true);
                break;
        }
    }
}
