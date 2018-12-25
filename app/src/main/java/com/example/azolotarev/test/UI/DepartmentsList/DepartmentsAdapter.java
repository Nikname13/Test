package com.example.azolotarev.test.UI.DepartmentsList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.azolotarev.test.Model.DepartmentModel;

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
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        View v=layoutInflater.inflate(android.R.layout.simple_list_item_1,viewGroup,false);
        return new ItemHolder(v,mClickListener);
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
    public ItemHolder(@NonNull View itemView, @NonNull ItemClickListener listemer) {
        super(itemView);
        mClickListener=listemer;
        mTextView=(TextView)itemView;
    }

    public void onBindViewHolder(DepartmentModel department){
        mDepartment=department;
    }

    @Override
    public void onClick(View v) {
        mClickListener.onClickItem(mDepartment);
    }
}
