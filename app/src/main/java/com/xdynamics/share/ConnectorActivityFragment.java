package com.xdynamics.share;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xdynamics.connector.utils.CRCUtils;


/**
 * A placeholder fragment containing a simple view.
 */
public class ConnectorActivityFragment extends Fragment {

    public ConnectorActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_connector, container, false);

        TextView txMsg = root.findViewById(R.id.tx_msg);

        String str = "sdghjkhdgfdghjhgfgf";

        StringBuilder builder = new StringBuilder(str);

        builder.append("<br>");

        builder.append("CRC16 code: \t");

        builder.append("<H3>");

        builder.append((int) CRCUtils.crc16(str));

        builder.append("</H3>");

        txMsg.setText(Html.fromHtml(builder.toString()));

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    private void init() {


    }


    @Override
    public void onStart() {
        super.onStart();


    }


}
