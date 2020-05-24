package ie.tcd.pavel;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.AxisTitle;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.Cursor;
import com.vaadin.flow.component.charts.model.DataLabels;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.PlotOptionsBar;
import com.vaadin.flow.component.charts.model.PlotOptionsPie;
import com.vaadin.flow.component.charts.model.Tooltip;
import com.vaadin.flow.component.charts.model.VerticalAlign;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import ie.tcd.pavel.documents.Group;
import ie.tcd.pavel.documents.User;
import ie.tcd.pavel.security.SecurityUtils;
import ie.tcd.pavel.utility.ExerciseTypes;

@Route("charts")
public class ExerciseChartsPage extends VerticalLayout {

	ExerciseTypes exerciseTypes;
    MongoDBOperations database;
    VerticalLayout verticalLayout= new VerticalLayout();
    HorizontalLayout horizontalLayout= new HorizontalLayout();
	
    public ExerciseChartsPage() {
    	database = BeanUtil.getBean(MongoDBOperations.class);
        exerciseTypes = BeanUtil.getBean(ExerciseTypes.class);
        String[] exercises = exerciseTypes.getExerciseTypes();  
    	List<Group> groups= database.getGroupsByUser(SecurityUtils.getUsername());
    	User currentUser= database.getUserByLogin(SecurityUtils.getUsername());
    	String currentUserString= currentUser.getForename()+ " "+ currentUser.getSurname();
    	Group currentGroup= groups.get(0);
    	List<User> users= database.getUsersByGroup(currentGroup.getName());  	
    	ArrayList<String> userList= new ArrayList<String>();
    	for(int i= 0; i<users.size(); i++)
    	{
    		userList.add(users.get(i).getForename()+ " "+ users.get(i).getSurname());
    	}  
    	String allUsersString= "";
    	for(int i= 0; i<userList.size(); i++)
    	{
    		allUsersString+= userList.get(i);
    		allUsersString+= "\n";
    	}
    	
    	ComboBox<String> comboBox= new ComboBox<>();
    	comboBox.setLabel("Exercise");
    	comboBox.setItems(exercises);
    	comboBox.addValueChangeListener(event ->{
    		Chart pieChart= new Chart(ChartType.PIE);
    		Configuration pieChartConfig= pieChart.getConfiguration();
    		pieChartConfig.setTitle("Member Contribution");
    		pieChartConfig.setSubTitle(comboBox.getValue());
    		
    		Tooltip tooltip = new Tooltip();
            tooltip.setValueDecimals(1);
            pieChartConfig.setTooltip(tooltip);
            
            PlotOptionsPie plotOptions = new PlotOptionsPie();
            plotOptions.setAllowPointSelect(true);
            plotOptions.setCursor(Cursor.POINTER);
            plotOptions.setShowInLegend(true);
            pieChartConfig.setPlotOptions(plotOptions);
            
            DataSeries series= new DataSeries();
            DataSeriesItem currentUserSeries= new DataSeriesItem(currentUserString, 0);
    	});
    	
    	Details memberDetails= new Details(); 
    	memberDetails.setSummaryText("Members");
    	memberDetails.addContent(new Text(allUsersString));
    	
    	verticalLayout.add(comboBox, memberDetails);
    	
    	//Template
    	/*int[] dataMember1= new int[] {200, 300, 22, 4, 400, 340, 6};
    	int[] dataMember2= new int[] {0, 30, 20, 1, 40, 170, 260};
    	int[] dataMember3= new int[] {100, 0, 20, 340, 200, 300, 57};
    	int[] dataMember4= new int[] {400, 3, 20, 23, 73, 250, 168};
    	int[] dataMember5= new int[] {150, 100, 30, 73, 45, 27, 221};
    	int[] dataMember6= new int[] {70, 75, 21, 3, 47, 25, 72};
    	
    	//Creating a drop down menu of all the exercises
    	ComboBox<String> comboBox= new ComboBox<>();
    	comboBox.setLabel("Exercise");
    	comboBox.setItems(exercises);   	
    	comboBox.addValueChangeListener(event ->{
    		//Adding bar chart
        	Chart barChart= new Chart();
        	verticalLayout.removeAll();
            Configuration configBarChart= barChart.getConfiguration();
            configBarChart.setTitle("Group Leaderboard");
            configBarChart.setSubTitle(comboBox.getValue());
            barChart.getConfiguration().getChart().setType(ChartType.PIE);
                       
            DataSeries dataSeries = new DataSeries();
            dataSeries.clear();
            for(int index= 0; index<=groupSize; index++)
            {
            	dataSeries.setData(dataMember1[index], dataMember2[index], dataMember3[index], dataMember4[index], dataMember5[index], dataMember6[index]); 
            	configBarChart.addSeries(dataSeries);
            }
            
            XAxis xAxis= new XAxis();
            xAxis.setCategories("Member 1", "Member 2", "Member 3", "Member 4", "Member 5", "Member 6");
            configBarChart.addxAxis(xAxis);
            
            YAxis yAxis= new YAxis();
            yAxis.setMin(0);
            AxisTitle yTitle= new AxisTitle();
            yTitle.setText("Future parameter");
            yTitle.setAlign(VerticalAlign.HIGH);
            yAxis.setTitle(yTitle);
            configBarChart.addyAxis(yAxis);
            
            Tooltip tooltip = new Tooltip();
            tooltip.setValueSuffix(" future parameter");
            tooltip.setShared(true);
            configBarChart.setTooltip(tooltip);
            
            PlotOptionsBar plotOptions= new PlotOptionsBar();
            DataLabels dataLabels= new DataLabels();
            dataLabels.setEnabled(true);
            plotOptions.setDataLabels(dataLabels);
            configBarChart.setPlotOptions(plotOptions);
            verticalLayout.add(barChart);
        });
    	add(comboBox, verticalLayout);*/
    }
}