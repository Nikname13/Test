package com.example.azolotarev.test.UI.Main.DepartmentsList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.azolotarev.test.Model.DepartmentModel;
import com.example.azolotarev.test.R;

import java.util.List;

public class DepartmentsAdapter  extends RecyclerView.Adapter<ItemHolder> {
    private List<DepartmentModel> mDepartments;
    private Context mContext;
    private ItemClickListener mClickListener;

    public DepartmentsAdapter(List<DepartmentModel> departments, Context context, ItemClickListener listener) {
        mClickListener=listener;
        mContext=context;
        setList(departments);
    }

    private void setList(List<DepartmentModel> departments) {
        mDepartments = departments;
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.e("TAG","departmentsadapter oncreateviewholder "+i);
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        View v=layoutInflater.inflate(R.layout.row_item,viewGroup,false);
        LinearLayout container=new LinearLayout(mContext);
        return new ItemHolder(v,mClickListener,container);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder departmentHolder, int i) {
        departmentHolder.onBindViewHolder(mDepartments.get(i));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDepartments.size();
    }
}
class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mTextView;
    private ItemClickListener mClickListener;
    private DepartmentModel mDepartment;
    private CardView mCardViewRoot,mChildrenCardView;
    private LinearLayout mLinerLayoutContainer;

    public ItemHolder(@NonNull View itemView, @NonNull ItemClickListener listener, @NonNull LinearLayout childrenContainer) {
        super(itemView);
        mClickListener=listener;
        mTextView=(TextView)itemView.findViewById(R.id.title_text_view);
        mCardViewRoot =(CardView)itemView.findViewById(R.id.root_card_view);
        itemView.setOnClickListener(this);
        mChildrenCardView=(CardView)itemView.findViewById(R.id.children_card_view);
        mLinerLayoutContainer=childrenContainer;
        mChildrenCardView.addView(mLinerLayoutContainer);
    }

    public void onBindViewHolder(DepartmentModel department){
        mDepartment=department;
        mTextView.setText(department.getName());
        mLinerLayoutContainer.setId(department.hashCode());
        mChildrenCardView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if(mChildrenCardView.getVisibility()==View.VISIBLE)
            mChildrenCardView.setVisibility(View.GONE);
        else{
            mChildrenCardView.setVisibility(View.VISIBLE);
            mClickListener.onClickItem(mDepartment,mLinerLayoutContainer.getId());
        }


    }
}
