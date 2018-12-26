package com.example.azolotarev.test.UI.Main.DepartmentsList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
        LayoutInflater layoutInflater=LayoutInflater.from(mContext);
        View v=layoutInflater.inflate(R.layout.row_item,viewGroup,false);
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
    private CardView mCardViewRoot;
    private LinearLayout mChildrenLayout;
    private RecyclerView mRecyclerViewChildren;
    public ItemHolder(@NonNull View itemView, @NonNull ItemClickListener listener) {
        super(itemView);
        mClickListener=listener;
        mTextView=(TextView)itemView.findViewById(R.id.title_text_view);
        mCardViewRoot =(CardView)itemView.findViewById(R.id.root_card_view);
        mCardViewRoot.setOnClickListener(this);
        mChildrenLayout=(LinearLayout)itemView.findViewById(R.id.children_fragment_container);
        mRecyclerViewChildren=(RecyclerView)itemView.findViewById(R.id.children_recycler_view);
    }

    public void onBindViewHolder(DepartmentModel department){
        mDepartment=department;
        mTextView.setText(department.getName());
    }

    @Override
    public void onClick(View v) {
        if(mChildrenLayout.getVisibility()==View.VISIBLE)
            mChildrenLayout.setVisibility(View.GONE);
        else{
            mChildrenLayout.setVisibility(View.VISIBLE);
        }
        mClickListener.onClickItem(mDepartment,mRecyclerViewChildren);

    }
}
