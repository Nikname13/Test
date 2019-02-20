package com.example.azolotarev.test.UI.Main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.example.azolotarev.test.Model.MapModel;
import com.example.azolotarev.test.R;

import java.util.List;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<Integer> mPositions;
    private List<Integer> mFilteredPositions;
    private Context mContext;
    private boolean mFiltered;
    private RecyclerItemContract mClickListener;

    public RecyclerListAdapter(@NonNull List<Integer> models, @NonNull Context context, @NonNull RecyclerItemContract listener) {
        mClickListener=listener;
        mContext=context;
        setList(models);
    }

    public void setList(List<Integer> positions) {
        mPositions = positions;
        mFilteredPositions=positions;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Log.e("TAG","departmentsadapter oncreateviewholder "+i);
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        return new ItemDepartmentHolder(layoutInflater.inflate(R.layout.departments_row_item,viewGroup,false),mClickListener,mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                    ItemDepartmentHolder holderD = (ItemDepartmentHolder) viewHolder;
                    holderD.onBindViewHolder(mFilteredPositions.get(i),mFiltered);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mFilteredPositions.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override protected FilterResults performFiltering(CharSequence constraint) {
                String sequenceString=constraint.toString();
                if(sequenceString.isEmpty()){
                    mFiltered=false;
                }
                else {
                    mFiltered=true;
                }
                mClickListener.onFilter(sequenceString, new RecyclerItemContract.RecyclerFilterCallback() {
                    @Override
                    public void onResult(List<Integer> filteredList) {
                        mFilteredPositions=filteredList;
                    }
                });
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notifyDataSetChanged();
            }
        };
    }
}
class ItemDepartmentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mTextView;
    private RecyclerItemContract mClickListener;
    private MapModel mItem;
    private CardView mCardViewRoot;
    private Context mContext;
    private static final int sMarginStart=50;

    public ItemDepartmentHolder(@NonNull View itemView, @NonNull RecyclerItemContract listener, Context context) {
        super(itemView);
        mClickListener=listener;
        mTextView=itemView.findViewById(R.id.title_text_view);
        mCardViewRoot =itemView.findViewById(R.id.root_card_view);
        mContext=context;
        itemView.setOnClickListener(this);

    }

    public void onBindViewHolder(int position, @NonNull boolean filtered){
        mClickListener.itemInPosition(new RecyclerItemContract.ItemInPositionCallback() {
            @Override
            public void onItem(@NonNull MapModel model) {
                mItem =model;
            }
        },
        position);

        mTextView.setText(mItem.getModel().getName());
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mCardViewRoot.getLayoutParams();

        if(!filtered) {
          //  Log.d("TAG","itemholder onBindView "+mItem.getModel().getName());
            layoutParams.setMarginStart(mItem.getLevel() * sMarginStart);
        }else{
            layoutParams.setMarginStart(0);
        }
        //mCardViewRoot.requestLayout();
        if(mItem.isSelected()) mCardViewRoot.setBackgroundColor(ContextCompat.getColor(mContext,R.color.colorAccent));
        else{
            mCardViewRoot.setBackground(ContextCompat.getDrawable(mContext,R.drawable.ripple_list));
        }
    }

    @Override
    public void onClick(View v) {
        mClickListener.onClickItem(mItem);
    }
}

