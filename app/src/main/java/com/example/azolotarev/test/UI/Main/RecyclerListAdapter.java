package com.example.azolotarev.test.UI.Main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.azolotarev.test.Model.MapModel;
import com.example.azolotarev.test.R;

import java.util.List;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Integer> mModels;
    private Context mContext;
    private RecyclerItemContract mClickListener;
    private int mViewType;

    public RecyclerListAdapter(@NonNull List<Integer> models, @NonNull Context context, @NonNull RecyclerItemContract listener) {
       // Log.d("TAG","recycler adapter constructor "+viewType);
        mClickListener=listener;
        mContext=context;
        mViewType=0;
        setList(models);
    }

    public void setList(List<Integer> models) {
        mModels = models;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.e("TAG","departmentsadapter oncreateviewholder "+i);
        LinearLayout container=new LinearLayout(mContext);
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        switch (mViewType){
            case 0:return new ItemDepartmentHolder(layoutInflater.inflate(R.layout.departments_row_item,viewGroup,false),mClickListener,container,mContext);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            switch (mViewType) {
                case 0:
                    ItemDepartmentHolder holderD = (ItemDepartmentHolder) viewHolder;
                    holderD.onBindViewHolder(mModels.get(i));
                    break;
                case 1:
                    ItemEmployeeHolder holderE = (ItemEmployeeHolder) viewHolder;
                    holderE.onBindViewHolder(mModels.get(i));
                    break;
            }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }


}
class ItemDepartmentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mTextView;
    private RecyclerItemContract mClickListener;
    private MapModel mDepartment;
    private CardView mCardViewRoot;
    private static final int sMarginStart=50;

    public ItemDepartmentHolder(@NonNull View itemView, @NonNull RecyclerItemContract listener, @NonNull LinearLayout childrenContainer, Context context) {
        super(itemView);
        mClickListener=listener;
        mTextView=itemView.findViewById(R.id.title_text_view);
        mCardViewRoot =itemView.findViewById(R.id.root_card_view);
        itemView.setOnClickListener(this);
        mCardViewRoot.setBackground(ContextCompat.getDrawable(context,R.drawable.ripple_list));
    }

    @SuppressLint("ResourceAsColor")
    public void onBindViewHolder(int position){
        mClickListener.itemInPosition(new RecyclerItemContract.itemInPositionCallback() {
            @Override
            public void onItem(@NonNull MapModel model) {
                mDepartment=model;
            }
        },
        position);
        Log.d("TAG","itemholder onBindView "+mDepartment.getModel().getName());
        mTextView.setText(mDepartment.getModel().getName());
        ViewGroup.MarginLayoutParams layoutParams= (ViewGroup.MarginLayoutParams) mCardViewRoot.getLayoutParams();
        layoutParams.setMarginStart(mDepartment.getLevel()*sMarginStart);
        mCardViewRoot.requestLayout();
        if(mDepartment.isSelected()) mCardViewRoot.setCardBackgroundColor(R.color.cardview_dark_background);
    }

    @Override
    public void onClick(View v) {
        mClickListener.onClickItem(mDepartment);
    }
}

class ItemEmployeeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView mTextView;
    private RecyclerItemContract mClickListener;
    private MapModel mModel;
    private CardView mRootCardView;

    public ItemEmployeeHolder(@NonNull View itemView, @NonNull RecyclerItemContract clickListener, Context context) {
        super(itemView);
        itemView.setOnClickListener(this);
        mTextView=itemView.findViewById(R.id.title_text_view);
        mClickListener=clickListener;
        mRootCardView=itemView.findViewById(R.id.root_card_view);
        mRootCardView.setBackground(ContextCompat.getDrawable(context,R.drawable.ripple_list));
       // Log.d("TAG","itemholder ItemEmployeeHolder create");
    }

    @SuppressLint("ResourceAsColor")
    public void onBindViewHolder(int entity){
/*        if(mModel==null && RecyclerLvl.elementIsOpen(entity)){
            mRootCardView.setBackgroundColor(R.color.colorPrimaryDark);
        }
        //Log.d("TAG","itemholderEmployee onBindView "+entity.getName()+ " - "+entity.hashCode());
        mModel= entity;
       // Log.e("TAG","itemholder onBindView "+entity.getName()+ " - "+entity.hashCode()+" id ");*/
        //mTextView.setText(mModel.getModel().getName());
    }

    @Override
    public void onClick(View v) {
/*        RecyclerLvl.setRecyclerLvl(mModel);
        mClickListener.onClickItem(mModel,R.id.fragmentContainer,getAdapterPosition());*/
    }
}

