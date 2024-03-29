package faturrahmanstoffel.jwork_android;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class MainListAdapter, untuk membuat list adapter pada MainActivity
 * @author Fatur Rahman Stoffel
 * @version 21-06-2021
 */

public class MainListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private ArrayList<Recruiter> _listDataHeader;

    private HashMap<Recruiter, ArrayList<Job>> _listDataChild;

    /**
     * Konstruktor MainListAdapter
     * @param context
     * @param listDataHeader
     * @param listChildData
     */
    public MainListAdapter(Context context, ArrayList<Recruiter> listDataHeader, HashMap<Recruiter, ArrayList<Job>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    /**
     * get child
     * @param groupPosition
     * @param childPosititon
     * @return listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon)
     */
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
    }

    /**
     * get child id
     * @param groupPosition
     * @param childPosition
     * @return childPosition
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * get child view
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return convertView
     */
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final Job childText = (Job) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.layout_job, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
        String s = "[" + childText.getId() + "] " + childText.getName() + ", Price : " + childText.getFee();
        txtListChild.setText(s);
        return convertView;
    }

    /**
     * get child count
     * @param groupPosition
     * @return listDataChild.get(this._listDataHeader.get(groupPosition)).size()
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    /**
     * get group
     * @param groupPosition
     * @return this._listDataHeader.get(groupPosition)
     */
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    /**
     * get group count
     * @return this.listDataHeader.size()
     */
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    /**
     * get group id
     * @param groupPosition
     * @return groupPosition
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * get group view
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return convertView
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        final Recruiter headerTitle = (Recruiter) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.layout_recuiter, null);
        }

        TextView ListItem = (TextView) convertView.findViewById(R.id.lblListItem);
        ListItem.setTypeface(null, Typeface.BOLD);
        ListItem.setText("Recruiter " + headerTitle.getName());

        return convertView;
    }

    /**
     * hash table id
     * @return boolean
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * selectable child
     * @param groupPosition
     * @param childPosition
     * @return boolean
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
