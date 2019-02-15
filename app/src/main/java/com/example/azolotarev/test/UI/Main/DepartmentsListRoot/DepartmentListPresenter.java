package com.example.azolotarev.test.UI.Main.DepartmentsListRoot;

import android.support.annotation.NonNull;
import android.util.Log;
import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractorContract;
import com.example.azolotarev.test.Model.EmployeeModel;
import com.example.azolotarev.test.Model.MapModel;
import com.example.azolotarev.test.UI.BaseView;
import com.example.azolotarev.test.UI.Main.RecyclerItemContract;

import java.util.ArrayList;
import java.util.List;

public class DepartmentListPresenter implements DepartmentListContract.Presenter {

    private DepartmentListContract.View mView;
    private final DepartmentInteractorContract mInteractor;
    private boolean mFirstLoad=true;
    private List<MapModel> mRecyclerModelList;
    private List<MapModel> mListOfSelected;
    private List<Integer> mFilteredList;
    private String mFilterString;

    public DepartmentListPresenter(@NonNull DepartmentInteractorContract interactor) {

        mInteractor = interactor;
        mInteractor.setProgressListener(this);
    }

    @Override
    public void start() {
      //  Log.e("TAG", "start department");
        mView.setFilterString(mFilterString);
        loadList(false || mFirstLoad);
        mFirstLoad=false;
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
    public void destroy() {
        
    }

    @Override
    public void onFilter(@NonNull String filterString, @NonNull RecyclerItemContract.RecyclerFilterCallback callback) {
      //  Log.d("TAG", "onFilter");
        if(!filterString.isEmpty()) {
            mFilteredList = new ArrayList<>();
            mInteractor.filteredList(filterString, new DepartmentInteractorContract.FilteredCallback() {
                @Override
                public void onFilteredList(List<MapModel> list) {
                    for (MapModel model : list) {
                        mFilteredList.add(mRecyclerModelList.indexOf(model));
                    }
                }
            });
            callback.onResult(mFilteredList);
        }else {
          //  Log.d("TAG", "onFilter else");
            callback.onResult(getPositionList());
            toClearFilter();
        }
    }

    @Override
    public void toClearFilter() {
        mFilteredList=null;
    }

    @Override
    public void itemInPosition(@NonNull RecyclerItemContract.ItemInPositionCallback callback, @NonNull int position) {
        callback.onItem(mRecyclerModelList.get(position));
    }

    @Override
    public void loadList(@NonNull final boolean freshUpdate) {
        if(freshUpdate) {
            mInteractor.refreshList();
            mListOfSelected=null;
        }
        mInteractor.loadList(new DepartmentInteractorContract.GetListCallback() {
            @Override
            public void onMapListLoaded(List<MapModel> list) {
                mRecyclerModelList=list;
               showList(freshUpdate);
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
        });
    }

    private void setSelectedItem(@NonNull MapModel item){
        if(mListOfSelected==null)mListOfSelected=new ArrayList<>();
        if(mListOfSelected.contains(item)) {
            for (MapModel model : mListOfSelected) {
                if (model.getModel().getId() != model.getModel().getId()) mListOfSelected.remove(model);
                else {
                    mListOfSelected.remove(model);
                    break;
                }
            }
        }else mListOfSelected.add(item);
        for (MapModel model : mListOfSelected) {
           // Log.d("TAG", " item "+model.getModel().getName() +" lvl "+mListOfSelected.indexOf(model));
        }
    }

    @Override
    public void openElementDetail(@NonNull MapModel selectedElement) {
        if(selectedElement.getModel() instanceof EmployeeModel){
            mView.showEmployeeDetail(String.valueOf(mRecyclerModelList.indexOf(selectedElement)),selectedElement.getId(),mFilterString);
        }else if(mRecyclerModelList.size()-1>mRecyclerModelList.indexOf(selectedElement) &&
                mRecyclerModelList.get(mRecyclerModelList.indexOf(selectedElement)).getLevel()!=mRecyclerModelList.get(mRecyclerModelList.indexOf(selectedElement)+1).getLevel()){
                openElement(
                        mRecyclerModelList.indexOf(selectedElement),
                        selectedElement.getLevel(),
                        !mRecyclerModelList.get(mRecyclerModelList.indexOf(selectedElement) + 1).isVisible());
                mView.updateList(getPositionList());
        }else{
            mView.showMessage("Пустой список");
        }
        setSelectedItem(selectedElement);
    }

    @Override
    public void applyFilter(@NonNull String query) {
        mView.applyFilter(query);
        mFilterString=query;
    }

    @Override
    public void showLogOutMessage() {
        mView.showLogOutMessage();
    }

    @Override
    public void logOut() {
        mInteractor.clearCredentials();
        mView.logOut();
    }

    private void openElement(int startPosition, int lvl, boolean visible){
        for(int i=startPosition+1;mRecyclerModelList.size()!=i && mRecyclerModelList.get(i).getLevel()!=lvl;i++){
            //Log.d("TAG","departments list presenter openElementDetail "+i);
            if(mRecyclerModelList.get(i).getLevel()==lvl+1) {
                if(mRecyclerModelList.get(i).isVisible()) openElement(i,mRecyclerModelList.get(i).getLevel(),!mRecyclerModelList.get(i).isVisible());
                mRecyclerModelList.get(i).setVisible(visible);
            }
        }
    }

    @Override
    public void showProgress() {
        mView.showProgress();
    }

    @Override
    public void hideProgress() {
        mView.hideProgress();
    }

    private void showList(boolean freshUpdate) {
        if(mListOfSelected!=null){
            for(MapModel model:mListOfSelected){
               openElement(mRecyclerModelList.indexOf(model),mRecyclerModelList.get(mRecyclerModelList.indexOf(model)).getLevel(),true);
            }
        }
        if(mFilteredList!=null){
            if(freshUpdate){
                onFilter(mFilterString, new RecyclerItemContract.RecyclerFilterCallback() {
                    @Override
                    public void onResult(List<Integer> filteredList) {
                        mFilteredList=filteredList;
                    }
                });
            }
            mView.showList(mFilteredList);
        }
        else mView.showList(getPositionList());
    }

    private List<Integer> getPositionList(){
        List<Integer> positionList = new ArrayList<>();
        for (MapModel model : mRecyclerModelList) {
            if (model.isVisible()){
                //Log.d("TAG", " position list "+ model.getModel().getName());
                positionList.add(mRecyclerModelList.indexOf(model));
                model.setSelected(false);
            }
        }
        if(mListOfSelected!=null && mListOfSelected.size()>=1 && mListOfSelected.get(mListOfSelected.size()-1).getModel() instanceof EmployeeModel)
            mRecyclerModelList.get(mRecyclerModelList.indexOf(mListOfSelected.get(mListOfSelected.size()-1))).setSelected(true);
        return positionList;
    }
}
