package com.pennapps.arpitsabherwal.journal;

/**
 * Created by arpitsabherwal on 24/01/16.
 */
  import android.content.Context;
    import android.content.Intent;
  import android.graphics.Typeface;
  import android.view.LayoutInflater;
    import android.view.View;
    import android.view.View.OnClickListener;
    import android.view.ViewGroup;
    import android.widget.AdapterView;
    import android.widget.AdapterView.OnItemClickListener;
    import android.widget.BaseAdapter;
  import android.widget.ImageView;
  import android.widget.TextView;

  import com.squareup.picasso.Picasso;

  import java.util.List;

public class CustomMultiAdapter extends BaseAdapter implements OnClickListener, OnItemClickListener {


    public class ViewHolder {
        TextView tvPara;
        ImageView ivImage;
		/*TextView txtTitle;
		TextView txtLoc;
		TextView txtTime;
		TextView txtCat;
		View	colourBar;
		TextView txtVenue;
		TextView txtCancel;
		Button btnMore;
		ArrayList<Item> gridArray = new ArrayList<Item>();
		LinearLayout llGrid;*/
        //NetworkImageView ivIcon;
    }
    public static final int TYPE_TWO = 2;
    public static final int TYPE_ONE = 1;

    Context context;
    ViewHolder holder = null;
    private List<RowItem> objects;
    View v;

    @Override
    public int getViewTypeCount() {
        return objects.size();
    }

    @Override
    public int getItemViewType(int position) {
        RowItem object;
        object=objects.get(position);
        return object.getType();
    }

    public CustomMultiAdapter(Context context, List<RowItem> objects) {
        this.context=context;
        this.objects = objects;
        //db= new DatabaseHandlerCalender(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        RowItem object;
        v=convertView;
        object=objects.get(position);
        int listViewItemType = object.getType();

        if (convertView == null) {
            if (listViewItemType == TYPE_ONE) {
                convertView = LayoutInflater.from(context).inflate(R.layout.row_item_text, null);
                holder = new ViewHolder();
                holder.tvPara=(TextView)convertView.findViewById(R.id.tvPara);
                Typeface type = Typeface.createFromAsset(context.getAssets(), "fonts/FreigSanProBook.otf");
                holder.tvPara.setTypeface(type);
                holder.tvPara.setText(object.getContent());
                convertView.setTag(holder);

                System.out.println("list_one");

            }
            else if (listViewItemType == TYPE_TWO) {
                convertView = LayoutInflater.from(context).inflate(R.layout.row_item_image, null);
                holder = new ViewHolder();
                holder.ivImage=(ImageView)convertView.findViewById(R.id.ivImage);
                Picasso.with(context).load(object.getContent()).into(holder.ivImage);
                convertView.setTag(holder);

                System.out.println("list_two");


            }
            else{
                holder = (ViewHolder) convertView.getTag();
                System.out.println("list_normal");

            }
        }
        return convertView;

    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return objects.indexOf(getItem(position));
    }



    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        // TODO Auto-generated method stub

    }

}
