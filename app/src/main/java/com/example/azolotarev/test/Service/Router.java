package com.example.azolotarev.test.Service;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;

import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.example.azolotarev.test.Data.Local.PersistentStorage;
import com.example.azolotarev.test.Data.Net.Connect;
import com.example.azolotarev.test.Data.Net.Net;
import com.example.azolotarev.test.Domain.Authorization.AuthorizationInteractor;
import com.example.azolotarev.test.Domain.DepartmentsList.DepartmentInteractor;
import com.example.azolotarev.test.Domain.EmployeePage.EmployeeInteractor;
import com.example.azolotarev.test.R;
import com.example.azolotarev.test.Repository.Repository;
import com.example.azolotarev.test.UI.Authorization.AuthorizationFragment;
import com.example.azolotarev.test.UI.Authorization.AuthorizationPresenter;
import com.example.azolotarev.test.UI.Main.DepartmentsListRoot.DepartmentListFragment;
import com.example.azolotarev.test.UI.Main.DepartmentsListRoot.DepartmentListPresenter;
import com.example.azolotarev.test.UI.Main.EmployeePage.LargeImage.LargeImageFragment;
import com.example.azolotarev.test.UI.Main.EmployeePage.LargeImage.LargeImagePresenter;
import com.example.azolotarev.test.UI.Main.EmployeeViewPager.EmployeePagerFragment;
import com.example.azolotarev.test.UI.Main.EmployeeViewPager.EmployeePagerPresenter;

public class Router {


    public static void showDepartmentsList(FragmentActivity activity, View sharedView) {
        DepartmentListFragment fragment=DepartmentListFragment.newInstance();
        PresenterManager.addPresenter(new DepartmentListPresenter(new DepartmentInteractor(
                        new Repository(PersistentStorage.get(),new Net(new Connect())))),
                fragment.getClass().getName());
        FragmentTransaction transaction=activity.getSupportFragmentManager().beginTransaction();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && sharedView!=null) {
            Transition changeTransform = TransitionInflater.from(ContextManager.getContext()).
                    inflateTransition(R.transition.change_image_transform);
            fragment.setSharedElementEnterTransition(changeTransform);
            transaction.addSharedElement(sharedView,ViewCompat.getTransitionName(sharedView));
        }
       // transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.fragmentContainer,fragment).commit();
    }

    public static void showEmployeeDetail(FragmentActivity activity, String positionInTree, String id, String filter) {
        EmployeePagerFragment fragment=EmployeePagerFragment.newInstance(positionInTree,id,filter);
        if(PresenterManager.getPresenter(fragment.getClass().getName())==null) {
            PresenterManager.addPresenter(new EmployeePagerPresenter(new EmployeeInteractor(
                            new Repository(PersistentStorage.get(),
                                    new Net(new Connect())))),
                    fragment.getClass().getName());
        }
        FragmentTransaction transaction=activity.getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragmentContainer, fragment).commit();
    }

    public static void showLogOut(FragmentActivity activity, View sharedView) {
        AuthorizationFragment fragment=AuthorizationFragment.newInstance();
        PresenterManager.addPresenter(new AuthorizationPresenter(new AuthorizationInteractor(new Repository(PersistentStorage.get(),new Net(new Connect())))),
                fragment.getClass().getName());
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
        FragmentTransaction transaction=activity.getSupportFragmentManager().beginTransaction();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && sharedView!=null) {
            Transition changeTransform = TransitionInflater.from(ContextManager.getContext()).
                    inflateTransition(R.transition.change_image_transform);
            fragment.setSharedElementEnterTransition(changeTransform);
            transaction.addSharedElement(sharedView,ViewCompat.getTransitionName(sharedView));
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.fragmentContainer,fragment).commit();

    }

    public static void showLargeImage(FragmentActivity activity, String id) {
        LargeImageFragment fragment=LargeImageFragment.newInstance(id);
        if(PresenterManager.getPresenter(fragment.getClass().getName())==null) {
            PresenterManager.addPresenter(new LargeImagePresenter(new EmployeeInteractor(
                            new Repository(PersistentStorage.get(),
                                    new Net(new Connect())))),
                    fragment.getClass().getName());
        }
        FragmentTransaction transaction=activity.getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.addToBackStack(null);
        transaction.add(R.id.fragmentContainer, fragment).commitAllowingStateLoss();
    }
}
