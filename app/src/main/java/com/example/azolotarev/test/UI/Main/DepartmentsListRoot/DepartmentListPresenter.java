package com.example.azolotarev.test.UI.Main.DepartmentsListRoot;

import android.support.annotation.NonNull;
import android.util.Log;
import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractorContract;
import com.example.azolotarev.test.Model.EmployeeModel;
import com.example.azolotarev.test.Model.RecyclerModel;
import com.example.azolotarev.test.UI.BaseView;
import com.example.azolotarev.test.UI.Main.RecyclerItemContract;

import java.util.ArrayList;
import java.util.List;

public class DepartmentListPresenter implements DepartmentListContract.Presenter {

    private DepartmentListContract.View mView;
    private final DepartmentInteractorContract mInteractor;
    private boolean mFirstLoad=false;
    private boolean mFreshUpdate=true;
    private List<RecyclerModel> mRecyclerModelList;
    private List<RecyclerModel> mListOfSelected;

    public DepartmentListPresenter(@NonNull DepartmentInteractorContract interactor) {

        mInteractor = interactor;
        mInteractor.setProgressListener(this);
    }

    @Override
    public void start() {
        Log.e("TAG", "start department");
        loadList(mFreshUpdate, mFirstLoad);
        mFreshUpdate=false;
    }

    @Override
    public void bindView(@NonNull BaseView view) {
        mView = (DepartmentListContract.View) view;
    }

    @Override
    public void unbindView() {
        mView=null;
    }

    @Override
    public void itemInPosition(@NonNull RecyclerItemContract.itemInPositionCallback callback, @NonNull int position) {
        callback.onItem(mRecyclerModelList.get(position));
    }

    @Override
    public void loadList(boolean freshUpdate, boolean firstLoad) {
        if(freshUpdate) mInteractor.refreshDepartments();
        mInteractor.getDepartments(new DepartmentInteractorContract.GetListCallback() {
            @Override
            public void onMapListLoaded(List<RecyclerModel> list) {
                mRecyclerModelList=list;
               showList();
            }

            @Override
            public void notAvailable(String errorMessage) {

            }

            @Override
            public void logOut(String errorMessage) {
                mView.showSuccessError(errorMessage);
            }

            @Override
            public void connectionError(String errorMessage) {
                mView.showConnectionError(errorMessage);
            }
        },
        firstLoad);
    }

    private void setSelectedItem(@NonNull RecyclerModel item){
        if(mListOfSelected==null)mListOfSelected=new ArrayList<>();
        if(mListOfSelected.contains(item)) {
            for (RecyclerModel model : mListOfSelected) {
                if (model.getModel().getId() != model.getModel().getId()) mListOfSelected.remove(model);
                else {
                    mListOfSelected.remove(model);
                    break;
                }
            }
        }else mListOfSelected.add(item);
        for (RecyclerModel model : mListOfSelected) {
            Log.d("TAG", " item "+model.getModel().getName() +" lvl "+mListOfSelected.indexOf(model));
        }
    }

    @Override
    public void openElementDetail(@NonNull RecyclerModel selectedElement) {
        if(selectedElement.getModel() instanceof EmployeeModel){
            mView.showEmployeeDetail(String.valueOf(selectedElement.getId()));
        }else if(mRecyclerModelList.size()-1>mRecyclerModelList.indexOf(selectedElement)){
            openElement(
                    mRecyclerModelList.indexOf(selectedElement),
                    selectedElement.getLevel(),
                    !mRecyclerModelList.get(mRecyclerModelList.indexOf(selectedElement) + 1).isVisible());
            mView.updateList(getPositionList());
        }
        setSelectedItem(selectedElement);
    }

    @Override
    public void logOut() {
        mInteractor.clearCredentials();
        mView.showAuthorization();
    }

    private void openElement(int startPosition, int lvl, boolean visible){
        for(int i=startPosition+1;mRecyclerModelList.size()!=i && mRecyclerModelList.get(i).getLevel()!=lvl;i++){
            Log.e("TAG","departments list presenter openElementDetail "+i);
            if(mRecyclerModelList.get(i).getLevel()==lvl+1) {
                if(mRecyclerModelList.get(i).isVisible()) openElement(i,mRecyclerModelList.get(i).getLevel(),!mRecyclerModelList.get(i).isVisible());
                mRecyclerModelList.get(i).setVisible(visible);
            }
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    private void showList() {
        if(mListOfSelected!=null){
            for(RecyclerModel model:mListOfSelected){
               openElement(mRecyclerModelList.indexOf(model),mRecyclerModelList.get(mRecyclerModelList.indexOf(model)).getLevel(),true);
            }
        }
        mView.showList(getPositionList());
    }

    private List<Integer> getPositionList(){
        List<Integer> positionList = new ArrayList<>();
        for (RecyclerModel model : mRecyclerModelList) {
            if (model.isVisible()){
                positionList.add(mRecyclerModelList.indexOf(model));
                model.setSelected(false);
            }
        }
        if(mListOfSelected!=null && mListOfSelected.size()>=1 && mListOfSelected.get(mListOfSelected.size()-1).getModel() instanceof EmployeeModel)
            mRecyclerModelList.get(mRecyclerModelList.indexOf(mListOfSelected.get(mListOfSelected.size()-1))).setSelected(true);
        return positionList;
    }
}
