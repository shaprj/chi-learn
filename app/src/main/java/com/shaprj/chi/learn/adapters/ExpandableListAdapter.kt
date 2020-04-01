package com.shaprj.chi.learn.adapters

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import com.shaprj.chi.learn.R
import com.shaprj.chi.learn.models.menu.ExpandedMenuItemModel
import com.shaprj.chi.learn.models.menu.MenuItemModel
import java.util.*

/*
 * Created by O.Shalaevsky on 4/28/2018
 */
class ExpandableListAdapter(
    private val mContext: Context,
    private val mListDataHeader: List<ExpandedMenuItemModel>?,
    private val mListDataChild: HashMap<ExpandedMenuItemModel, List<MenuItemModel>>,
    internal var expandList: ExpandableListView
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        val i = mListDataHeader?.size ?: 0
        Log.d("GROUPCOUNT", i.toString())
        return this.mListDataHeader!!.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        var childCount = 0
        if (groupPosition != 2) {
            try {
                childCount = this.mListDataChild[this.mListDataHeader!![groupPosition]]!!
                    .size
            } catch (npe: NullPointerException) {
                childCount = 0
            }

        }
        return childCount
    }

    override fun getGroup(groupPosition: Int): Any {
        return this.mListDataHeader!![groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        Log.d("CHILD", mListDataChild[this.mListDataHeader!![groupPosition]]!![childPosition].toString())
        return this.mListDataChild[this.mListDataHeader[groupPosition]]!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val headerTitle = getGroup(groupPosition) as ExpandedMenuItemModel
        if (convertView == null) {
            val infalInflater = this.mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.list_header, null)
        }
        val lblListHeader = convertView!!
            .findViewById(R.id.submenu) as TextView
        val expcolIcon = convertView.findViewById(R.id.iconexpcoll) as ImageView
        val headerIcon = convertView.findViewById(R.id.iconimage) as ImageView
        lblListHeader.setTypeface(null, Typeface.BOLD)
        lblListHeader.setText(headerTitle.itemName)
        headerIcon.setImageResource(headerTitle.itemIcon)
        setExpColIcon(expcolIcon, headerTitle, isExpanded)
        return convertView
    }

    private fun setExpColIcon(imageView: ImageView, expandedMenuItemModel: ExpandedMenuItemModel, isExpanded: Boolean) {
        if (isExpanded) {
            if (expandedMenuItemModel.expandedIcon !== -1) {
                imageView.setImageResource(expandedMenuItemModel.expandedIcon)
            }
        } else {
            if (expandedMenuItemModel.collapsedIcon !== -1) {
                imageView.setImageResource(expandedMenuItemModel.collapsedIcon)
            }
        }
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val childModel = getChild(groupPosition, childPosition) as MenuItemModel

        if (convertView == null) {
            val infalInflater = this.mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.list_submenu, null)
        }

        val listChildText = convertView!!
            .findViewById(R.id.submenu) as TextView

        listChildText.setText(childModel.itemName)
        val imageChildText = convertView
            .findViewById(R.id.subiconimage) as ImageView

        imageChildText.setImageResource(childModel.itemIcon)

        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}