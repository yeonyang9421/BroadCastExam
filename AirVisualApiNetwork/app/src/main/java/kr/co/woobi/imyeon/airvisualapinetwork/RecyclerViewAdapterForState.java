package kr.co.woobi.imyeon.airvisualapinetwork;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterForState extends RecyclerView.Adapter<RecyclerViewAdapterForState.ViewHolder> {
    List<String> mItem=new ArrayList<>();

  /*  interface onFindDustInfoListener{
        void onFindDustInfo(String item);
    }
    private onFindDustInfoListener mListener;
    public void setOnFindDustInfo(onFindDustInfoListener listener){
        mListener=listener;
    }*/

    public RecyclerViewAdapterForState(List<String> mItem) {
        this.mItem = mItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_find_city,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        String str=mItem.get(i);
        viewHolder.textItem.setText(str);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item=mItem.get(viewHolder.getAdapterPosition());
                Toast.makeText(v.getContext(), ""+item, Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new EventStateItem(mItem.get(viewHolder.getAdapterPosition())));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textItem=itemView.findViewById(R.id.text_item);
        }
    }
}
