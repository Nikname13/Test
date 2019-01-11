package com.example.azolotarev.test.UI.Main;

import android.content.Context;
import android.graphics.drawable.RippleDrawable;
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
import com.example.azolotarev.test.Model.BaseModel;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.Model.EmployeeModel;
import com.example.azolotarev.test.R;

import java.util.List;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BaseModel> mModels;
    private Context mContext;
    private ItemClickListener mClickListener;
    private int mViewType;

    public RecyclerListAdapter(@NonNull List<BaseModel> models, @NonNull Context context, @NonNull ItemClickListener listener, @NonNull int viewType) {
       // Log.d("TAG","recycler adapter constructor "+viewType);
        mClickListener=listener;
        mContext=context;
        mViewType=viewType;
        setList(models);
    }

    private void setList(List<BaseModel> models) {
        mModels = models;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Log.e("TAG","departmentsadapter oncreateviewholder "+i);
        LinearLayout container=new LinearLayout(mContext);
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        switch (mViewType){
            case 0:return new ItemDepartmentHolder(layoutInflater.inflate(R.layout.departments_row_item,viewGroup,false),mClickListener,container,mContext);
            case 1:return new ItemEmployeeHolder(layoutInflater.inflate(R.layout.employee_row_item,viewGroup,false),mClickListener,mContext);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (mViewType){
            case 0:
                ItemDepartmentHolder holderD= (ItemDepartmentHolder) viewHolder;
                holderD.onBindViewHolder(mModels.get(i));
                break;
            case 1:
                ItemEmployeeHolder holderE= (ItemEmployeeHolder) viewHolder;
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
    private ItemClickListener mClickListener;
    private BaseModel mDepartment;
    private CardView mCardViewRoot,mChildrenCardView;
    private LinearLayout mLinerLayoutContainer;

    public ItemDepartmentHolder(@NonNull View itemView, @NonNull ItemClickListener listener, @NonNull LinearLayout childrenContainer, Context context) {
        super(itemView);
        mClickListener=listener;
        mTextView=(TextView)itemView.findViewById(R.id.title_text_view);
        mCardViewRoot =(CardView)itemView.findViewById(R.id.root_card_view);
        itemView.setOnClickListener(this);
        mChildrenCardView=(CardView)itemView.findViewById(R.id.children_card_view);
        mLinerLayoutContainer=childrenContainer;
        mChildrenCardView.addView(mLinerLayoutContainer);
        mCardViewRoot.setBackground(ContextCompat.getDrawable(context,R.drawable.ripple_list));


    }

    public void onBindViewHolder(BaseModel entity){
       // Log.e("TAG","itemholder onBindView "+department.getName()+ " - "+department.hashCode()+" id "+mLinerLayoutContainer.getId());
        mClickListener.removeFragment(mLinerLayoutContainer.getId());
        mDepartment=entity;
        mTextView.setText(mDepartment.getName());
        mLinerLayoutContainer.setId(entity.hashCode());
        mChildrenCardView.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        if(mChildrenCardView.getVisibility()==View.VISIBLE) {
            mChildrenCardView.setVisibility(View.GONE);
            RecyclerLvl.removeLvl(mDepartment);
        }
        else{
            RecyclerLvl.setRecyclerLvl(mDepartment);
            mChildrenCardView.setVisibility(View.VISIBLE);
            mClickListener.onClickItem(mDepartment,mLinerLayoutContainer.getId());
        }


    }

}

class ItemEmployeeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView mTextView;
    private ItemClickListener mClickListener;
    private BaseModel mModel;
    private CardView mRootCardView;

    public ItemEmployeeHolder(@NonNull View itemView,@NonNull ItemClickListener clickListener, Context context) {
        super(itemView);
        itemView.setOnClickListener(this);
        mTextView=(TextView)itemView.findViewById(R.id.title_text_view);
        mClickListener=clickListener;
        mRootCardView=(CardView)itemView.findViewById(R.id.root_card_view);
        mRootCardView.setBackground(ContextCompat.getDrawable(context,R.drawable.ripple_list));
    }

    public void onBindViewHolder(BaseModel entity){
        mModel= entity;
       // Log.e("TAG","itemholder onBindView "+entity.getName()+ " - "+entity.hashCode()+" id ");
        mTextView.setText(mModel.getName());
    }

    @Override
    public void onClick(View v) {
        mClickListener.onClickItem(mModel,R.id.fragmentContainer);
    }
}

