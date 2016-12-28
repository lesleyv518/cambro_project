package com.cambro.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.cambro.app.adapter.ViewPagerTransportAdapter;
import com.cambro.app.interfce.OnFragmentInteractionListener;
import com.cambro.app.model.FresFit;
import com.cambro.app.model.Transport;

import java.util.ArrayList;
import java.util.List;


public class TransportActivity extends FragmentActivity implements OnFragmentInteractionListener {

    private String titles[];
    ViewPagerTransportAdapter adp;
    private String category;
    private List<Integer> lstFragHistory = new ArrayList<>();
    private Integer current_fragmentId = 0;
    private boolean electric = true;
    private String pans;
    private String deep;
    private boolean smallDeep;
    private Transport transport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);

        findViewById(R.id.tp_iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lstFragHistory.size()==1){
                    finish();
                    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                }
                else
                {
                    lstFragHistory.remove(lstFragHistory.size() - 1);
                    FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
                    tr.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out);
                    current_fragmentId = lstFragHistory.get(lstFragHistory.size() - 1);
                    tr.replace(R.id.fragment_container_transport, adp.getItem(current_fragmentId));
                    tr.addToBackStack(null);
                    tr.commit();
                    if (current_fragmentId != 0)
                    {
                        findViewById(R.id.tp_txt_title).setVisibility(View.VISIBLE);
                        findViewById(R.id.tp_v_titlebar).setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        findViewById(R.id.tp_txt_title).setVisibility(View.INVISIBLE);
                        findViewById(R.id.tp_v_titlebar).setVisibility(View.INVISIBLE);
                    }

                }

            }
        });

        titles = new String[]{"a", "b", "c"};
        adp = new ViewPagerTransportAdapter("dash", getSupportFragmentManager(), titles);
        FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
        tr.add(R.id.fragment_container_transport, adp.getItem(0)).commit();
        setTPTitle(0);
    }

    @Override
    public void onFragmentInteraction(String uri, boolean blFront) {
        if (uri.equals("finish")) {
            finish();
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        } else {
            int i = Integer.parseInt(uri);

            FragmentTransaction tr = getSupportFragmentManager().beginTransaction();
            if (blFront) tr.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
            else tr.setCustomAnimations(R.anim.push_right_in, R.anim.push_right_out);
            tr.replace(R.id.fragment_container_transport, adp.getItem(i));
            tr.addToBackStack(null);
            tr.commit();

        }
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isElectric() {
        return electric;
    }

    public void setElectric(boolean electric) {
        this.electric = electric;
    }


    public String getPans() {
        return pans;
    }

    public void setPans(String pans) {
        this.pans = pans;
    }

    public String getDeep() {
        return deep;
    }

    public void setDeep(String deep) {
        this.deep = deep;
    }

    public boolean isSmallDeep() {
        return smallDeep;
    }

    public void setSmallDeep(boolean smallDeep) {
        this.smallDeep = smallDeep;
    }

    public void setTPTitle(Integer fragId)
    {
        lstFragHistory.add(fragId);
        this.current_fragmentId = fragId;
        if (this.current_fragmentId > 0)
        {
            findViewById(R.id.tp_txt_title).setVisibility(View.VISIBLE);
            findViewById(R.id.tp_v_titlebar).setVisibility(View.VISIBLE);
            if(fragId == 6)
                ((TextView)findViewById(R.id.tp_txt_title)).setText(getString(R.string.tp_txt_gafq));
            else
                ((TextView)findViewById(R.id.tp_txt_title)).setText(getString(R.string.tp_txt_title));
        }
        else
        {
            findViewById(R.id.tp_txt_title).setVisibility(View.INVISIBLE);
            findViewById(R.id.tp_v_titlebar).setVisibility(View.INVISIBLE);
        }
    }


}
