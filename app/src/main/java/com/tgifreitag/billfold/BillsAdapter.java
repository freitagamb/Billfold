package com.tgifreitag.billfold;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.BillsViewHolder> {


    protected List<BillsInfo> billList;
    public BillsAdapter(List<BillsInfo> billList) {
        this.billList = billList;
    }

    @Override
    public void onBindViewHolder(BillsViewHolder holder, int i) {
        BillsInfo current = billList.get(i);
        holder.billName.setText(current.billName);
        holder.monthName.setText(current.monthName);
        holder.dayNum.setText(current.dayNum);
        holder.amount.setText(current.amount);
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    @Override
    public BillsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.activity_bills_tile, viewGroup, false);

        return new BillsViewHolder(itemView);
    }

    class BillsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView billName;
        TextView monthName;
        TextView dayNum;
        TextView amount;
        CardView cv;

        public BillsViewHolder(View itemView) {
            super(itemView);
            billName = (TextView) itemView.findViewById(R.id.billNametxt);
            monthName = (TextView) itemView.findViewById(R.id.monthNametxt);
            dayNum = (TextView) itemView.findViewById(R.id.dayNumtxt);
            amount = (TextView) itemView.findViewById(R.id.amounttxt);
            cv = (CardView) itemView.findViewById(R.id.bills_CardView);
        }

        public void onClick(View v) {
            Log.d("inonclick", "inOnclick");
            Context context = v.getContext();
            Intent intObj = new Intent(context,HomeActivity.class);
          //  intObj.putExtra("pos", (int) getPosition());
            context.startActivity(intObj);
        }

    }

}

