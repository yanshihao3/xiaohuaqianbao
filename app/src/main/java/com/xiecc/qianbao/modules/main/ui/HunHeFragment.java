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
public class HunHeFragment extends Fragment {


    @Bind(R.id.nxx1)
    TextView nxx1;
    @Bind(R.id.nxvx1)
    Spinner nxvx1;
    @Bind(R.id.tableRowx7)
    TableRow tableRowx7;
    @Bind(R.id.zex1)
    TextView zex1;
    @Bind(R.id.zevx1)
    EditText zevx1;
    @Bind(R.id.tableRowx1)
    TableRow tableRowx1;
    @Bind(R.id.lvx1)
    TextView lvx1;
    @Bind(R.id.lvvvx1)
    EditText lvvvx1;
    @Bind(R.id.tableRowx3)
    TableRow tableRowx3;
    @Bind(R.id.zex2)
    TextView zex2;
    @Bind(R.id.zevx2)
    EditText zevx2;
    @Bind(R.id.tableRowx5)
    TableRow tableRowx5;
    @Bind(R.id.lvx2)
    TextView lvx2;
    @Bind(R.id.lvvx2)
    Spinner lvvx2;
    @Bind(R.id.lvvvx2)
    EditText lvvvx2;
    @Bind(R.id.tableRowx6)
    TableRow tableRowx6;
    @Bind(R.id.subx1)
    Button subx1;
    @Bind(R.id.cancelx1)
    Button cancelx1;
    @Bind(R.id.tableRowx4)
    TableRow tableRowx4;
    @Bind(R.id.tx1)
    TableLayout tx1;
    @Bind(R.id.widget_layout_mix)
    LinearLayout widgetLayoutMix;
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

    public HunHeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hun_he, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, nx);
        // 设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 将adapter 添加到spinner中
        nxvx1.setAdapter(adapter);
        nxvx1.setSelection(7, true);
        // 设置默认值
        nxvx1.setVisibility(View.VISIBLE);
        // 添加事件Spinner事件监听
        nxvx1.setOnItemSelectedListener(new SpinnerSelectedListener1(lvvvx1));
        // 将可选内容与ArrayAdapter连接起来
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, lv);
        // 设置下拉列表的风格
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 将adapter 添加到spinner中
        lvvx2.setAdapter(adapter1);
        lvvx2.setSelection(6, true);
        // 设置默认值
        lvvx2.setVisibility(View.VISIBLE);
        // 添加事件Spinner事件监听
        lvvx2.setOnItemSelectedListener(new SpinnerSelectedListener(lvvvx2));



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

    @OnClick({R.id.subx1, R.id.cancelx1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.subx1:
                Editable value = zevx1.getText();
                if (value.toString() == null || value.toString().length() <= 0) {
                    new AlertDialog.Builder(getContext()).setTitle("提示")
                            .setMessage("公积金贷款总额不能为空")
                            .setPositiveButton("OK", null).show();
                    return;
                }

                Editable value1 = zevx2.getText();
                if (value1.toString() == null
                        || value1.toString().length() <= 0) {
                    new AlertDialog.Builder(getActivity()).setTitle("提示")
                            .setMessage("商业贷款总额不能为空")
                            .setPositiveButton("OK", null).show();
                    return;
                }

                Editable value2 = lvvvx1.getText();
                if (value2.toString() == null
                        || value2.toString().length() <= 0) {
                    new AlertDialog.Builder(getActivity()).setTitle("提示")
                            .setMessage("公积金利率不能为空")
                            .setPositiveButton("OK", null).show();
                    return;
                }

                Editable value3 = lvvvx2.getText();
                if (value3.toString() == null
                        || value3.toString().length() <= 0) {
                    new AlertDialog.Builder(getActivity()).setTitle("提示")
                            .setMessage("商贷利率不能为空")
                            .setPositiveButton("OK", null).show();
                    return;
                }

                double nx = Double.parseDouble(nxvx1.getSelectedItem()
                        .toString()) * 12;
                double ze = Double.parseDouble(value.toString());
                double rate = Double.parseDouble(lvvvx1.getText().toString()) / 100;
                double ze1 = Double.parseDouble(value1.toString());
                double rate1 = Double.parseDouble(lvvvx2.getText().toString()) / 100;

                cal(ze, nx, rate, ze1, rate1);

                break;
            case R.id.cancelx1:

                zevx1.setText("");
                zevx2.setText("");
                nxvx1.setSelection(7, true);
                lvvx2.setSelection(6, true);

                break;
        }
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
}
