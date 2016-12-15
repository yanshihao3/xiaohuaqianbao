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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.xiecc.qianbao.R;

import java.math.BigDecimal;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class shangyeFragment extends Fragment {


    @Bind(R.id.ze1)
    TextView ze1;
    @Bind(R.id.zev1)
    EditText zev1;
    @Bind(R.id.tableRow1)
    TableRow tableRow1;
    @Bind(R.id.nx1)
    TextView nx1;
    @Bind(R.id.nxv1)
    Spinner nxv1;
    @Bind(R.id.tableRow2)
    TableRow tableRow2;
    @Bind(R.id.lv1)
    TextView lv1;
    @Bind(R.id.lvv1)
    Spinner lvv1;
    @Bind(R.id.lvvv1)
    EditText lvvv1;
    @Bind(R.id.tableRow3)
    TableRow tableRow3;
    @Bind(R.id.sub1)
    Button sub1;
    @Bind(R.id.cancel1)
    Button cancel1;
    @Bind(R.id.tableRow4)
    TableRow tableRow4;
    @Bind(R.id.t1)
    TableLayout t1;
    @Bind(R.id.widget_layout_sy)
    LinearLayout widgetLayoutSy;
    @Bind(R.id.tableRow5)
    TableRow tableRow5;
    @Bind(R.id.am10)
    TextView am10;
    @Bind(R.id.am20)
    TextView am20;
    @Bind(R.id.tableRow6)
    TableRow tableRow6;
    @Bind(R.id.am11)
    TextView am11;
    @Bind(R.id.am21)
    TextView am21;
    @Bind(R.id.tableRow7)
    TableRow tableRow7;
    @Bind(R.id.am12)
    TextView am12;
    @Bind(R.id.am22)
    TextView am22;
    @Bind(R.id.tableRow8)
    TableRow tableRow8;
    @Bind(R.id.am13)
    TextView am13;
    @Bind(R.id.am23)
    TextView am23;
    @Bind(R.id.tableRow9)
    TableRow tableRow9;
    @Bind(R.id.am14)
    TextView am14;
    @Bind(R.id.am24)
    TextView am24;
    @Bind(R.id.tableRow10)
    TableRow tableRow10;
    @Bind(R.id.t2)
    TableLayout t2;
    @Bind(R.id.tableRow12)
    TableRow tableRow12;


    private static final String[] nx = {"1", "2", "3", "4", "5", "10", "15",
            "20", "25", "30"};
    private static final String[] lv = {"最新基准利率7折", "最新基准利率7.5折", "最新基准利率8折",
            "最新基准利率8.5折", "最新基准利率9折", "最新基准利率9.5折", "最新基准利率", "最新基准利率1.1倍",
            "最新基准利率1.2倍", "最新基准利率1.3倍"};


    public shangyeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shangye, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {

        // 将可选内容与ArrayAdapter连接起来
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, nx);
        // 设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 将adapter 添加到spinner中
        nxv1.setAdapter(adapter);
        nxv1.setSelection(7, true);
        // 设置默认值
        nxv1.setVisibility(View.VISIBLE);

        // 将可选内容与ArrayAdapter连接起来
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, lv);
        // 设置下拉列表的风格
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 将adapter 添加到spinner中
        lvv1.setAdapter(adapter1);
        lvv1.setSelection(6, true);
        // 设置默认值
        lvv1.setVisibility(View.VISIBLE);
        // 添加事件Spinner事件监听
        lvv1.setOnItemSelectedListener(new SpinnerSelectedListener(lvvv1));


    }

    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        EditText et;

        public SpinnerSelectedListener(EditText et) {
            this.et = et;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            et.setText(new BigDecimal(getlvv1(position)).setScale(2,
                    BigDecimal.ROUND_HALF_DOWN).toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }

    }
    public double getlvv1(int a) {
        double value = 6.55f;
        switch (a) {
            case 0:
                value = 6.55 * 0.7;
                return value;
            case 1:
                value = 6.55 * 0.75;
                return value;
            case 2:
                value = 6.55 * 0.8;
                return value;
            case 3:
                value = 6.55 * 0.85;
                return value;
            case 4:
                value = 6.55 * 0.9;
                return value;
            case 5:
                value = 6.55 * 0.95;
                return value;
            case 6:
                value = 6.55 * 1;
                return value;
            case 7:
                value = 6.55 * 1.1;
                return value;
            case 8:
                value = 6.55 * 1.2;
                return value;
            case 9:
                value = 6.55 * 1.3;
                return value;
            default:
                return value;
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



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.sub1, R.id.cancel1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sub1:
                Editable value = zev1.getText();
                if (value.toString() == null || value.toString().length() <= 0) {
                    new AlertDialog.Builder(getActivity()).setTitle("提示")
                            .setMessage("贷款总额不能为空")
                            .setPositiveButton("OK", null).show();
                    return;
                }
                Editable value1 = lvvv1.getText();
                if (value1.toString() == null
                        || value1.toString().length() <= 0) {
                    new AlertDialog.Builder(getActivity()).setTitle("提示")
                            .setMessage("利率不能为空").setPositiveButton("OK", null)
                            .show();
                    return;
                }
                double ze = Double.parseDouble(value.toString());
                double nx = Double.parseDouble(nxv1.getSelectedItem()
                        .toString()) * 12;
                double rate = Double.parseDouble(lvvv1.getText().toString()) / 100;

                cal(ze, nx, rate);
                break;
            case R.id.cancel1:
                zev1.setText("");
                nxv1.setSelection(7, true);
                lvv1.setSelection(6, true);
                break;
        }
    }
}
