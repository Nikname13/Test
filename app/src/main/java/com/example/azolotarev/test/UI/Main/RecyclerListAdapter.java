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
import com.example.azolotarev.test.Model.BaseModel;
import com.example.azolotarev.test.R;

import java.util.List;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BaseModel> mModels;
    private Context mContext;
    private RecyclerItemContract mClickListener;
    private int mViewType;

    public RecyclerListAdapter(@NonNull List<BaseModel> models, @NonNull Context context, @NonNull RecyclerItemContract listener, @NonNull int viewType) {
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
        Log.e("TAG","departmentsadapter oncreateviewholder "+i);
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
    private RecyclerItemContract mClickListener;
    private BaseModel mDepartment;
    private CardView mCardViewRoot, mChildrenContainer;
    private LinearLayout mLinerLayoutContainer;

    public ItemDepartmentHolder(@NonNull View itemView, @NonNull RecyclerItemContract listener, @NonNull LinearLayout childrenContainer, Context context) {
        super(itemView);
        mClickListener=listener;
        mTextView=itemView.findViewById(R.id.title_text_view);
        mCardViewRoot =itemView.findViewById(R.id.root_card_view);
        itemView.setOnClickListener(this);
        mChildrenContainer =itemView.findViewById(R.id.children_card_view);
        mLinerLayoutContainer=childrenContainer;
        mChildrenContainer.addView(mLinerLayoutContainer);
        mCardViewRoot.setBackground(ContextCompat.getDrawable(context,R.drawable.ripple_list));
       // Log.d("TAG","itemholder ItemDepartmentHolder create  id "+mLinerLayoutContainer.getId());
    }

    public void onBindViewHolder(BaseModel entity){
        //Log.d("TAG","itemholder onBindView ");
        mClickListener.removeFragment(entity);
        mLinerLayoutContainer.setId(entity.hashCode());
        mTextView.setText(entity.getName());
        if(mDepartment==null && RecyclerLvl.elementIsOpen(entity)){

            openElement(entity);
        }else mChildrenContainer.setVisibility(View.GONE);
       // if(mDepartment!=null) Log.d("TAG","itemholder onBindView "+entity.getName()+ " - "+entity.getId()+" model "+mDepartment.getId()+" "+mDepartment.getName());
        mDepartment=entity;
    }



    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        if(mChildrenContainer.getVisibility()==View.VISIBLE) {
            mChildrenContainer.setVisibility(View.GONE);
            RecyclerLvl.removeLvl(mDepartment);
        }
        else{
            RecyclerLvl.setRecyclerLvl(mDepartment);
            openElement(mDepartment);
        }
    }
    private void openElement(@NonNull BaseModel model){
        mClickListener.scrollToPosition(getAdapterPosition());
        mChildrenContainer.setVisibility(View.VISIBLE);
        mClickListener.onClickItem(model,mLinerLayoutContainer.getId());
    }

}

class ItemEmployeeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView mTextView;
    private RecyclerItemContract mClickListener;
    private BaseModel mModel;
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

    public void onBindViewHolder(BaseModel entity){
        //Log.d("TAG","itemholderEmployee onBindView "+entity.getName()+ " - "+entity.hashCode());
        mModel= entity;
       // Log.e("TAG","itemholder onBindView "+entity.getName()+ " - "+entity.hashCode()+" id ");
        mTextView.setText(mModel.getName());
    }

    @Override
    public void onClick(View v) {
        mClickListener.onClickItem(mModel,R.id.fragmentContainer);
    }
}

