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
import com.example.azolotarev.test.Model.RecyclerModel;
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
            case 1:return new ItemEmployeeHolder(layoutInflater.inflate(R.layout.employee_row_item,viewGroup,false),mClickListener,mContext);
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
    private RecyclerModel mDepartment;
    private CardView mCardViewRoot, mChildrenContainer;
    private LinearLayout mLinerLayoutContainer;
    private int mPosition;

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

    public void onBindViewHolder(int position){
        mClickListener.itemInPosition(new RecyclerItemContract.itemInPositionCallback() {
            @Override
            public void onItem(@NonNull RecyclerModel model) {
                mDepartment=model;
            }
        },
        position);
        Log.d("TAG","itemholder onBindView "+mDepartment.getModel().getName());
        mTextView.setText(mDepartment.getModel().getName());
        //mClickListener.removeFragment(entity);
        ViewGroup.MarginLayoutParams layoutParams= (ViewGroup.MarginLayoutParams) mCardViewRoot.getLayoutParams();
        layoutParams.setMarginStart(mDepartment.getLevel()*100);
        RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) itemView.getLayoutParams();
        mCardViewRoot.requestLayout();
      /*  mLinerLayoutContainer.setId(entity.hashCode());
        mTextView.setText(entity.getModel().getName());
        if(!entity.isVisible()){
            itemView.setVisibility(View.GONE);
            params.height=0;
            params.width=0;
        }
        itemView.setLayoutParams(params);*/
        /*if(mDepartment==null && RecyclerLvl.elementIsOpen(entity)){
            mPosition=getAdapterPosition();
            openElement(entity);
        }else mChildrenContainer.setVisibility(View.GONE);
       // if(mDepartment!=null) Log.d("TAG","itemholder onBindView "+entity.getName()+ " - "+entity.getId()+" model "+mDepartment.getId()+" "+mDepartment.getName());
        mDepartment=entity;*/
    }

    @Override
    public void onClick(View v) {
            openElement(mDepartment);
    }
    private void openElement(@NonNull RecyclerModel model){
        Log.d("TAG","OPEN ELEMENT");
        //mClickListener.scrollToPosition(getAdapterPosition());
       // mChildrenContainer.setVisibility(View.VISIBLE);
        mClickListener.onClickItem(model);
    }

}

class ItemEmployeeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView mTextView;
    private RecyclerItemContract mClickListener;
    private RecyclerModel mModel;
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

