<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Main Page</title>
</head>
<body>
	<f:view>
		<h:form>
			<hr>
			<div align="center">
				<h:graphicImage value="images/twitterminer.png" height="363"
					width="272" />
				<div align="center">
					<span><font size="3.5" color="Chocolate "> <b><u>Team
									Members :</u></b> <br>Krishnakanth Panakarajupally (kpanak2)<br>Sreekanth
							Chinthagunta (schint9)<br> Manikandan Thandalam
							Mohanasundaram (mthand2)
					</font> </span>
				</div>
				<hr>
				<hr>
				<h:panelGrid columns="2">
					<h:outputText value="Keyword : "
						style="font-weight:bold;color:Maroon;width:240px;height:25px" />
					<h:inputText id="keyword" value="#{streamingHeatMap.keyword}"
						required="true" requiredMessage="Keyword is required"
						label="Keyword" maxlength="30"
						style="font-weight:bold;width:240px;height:25px" />
					<h:outputText value=""></h:outputText>
					<h:message for="keyword" style="color:red;font-weight:bold" />
					<h:outputText value="Number of tweets : "
						style="font-weight:bold;color:Maroon;width:240px;height:25px" />
					<h:inputText id="numOfTweets"
						value="#{streamingHeatMap.numberOfTweets}" required="true"
						requiredMessage="Number is required" label="Keyword"
						maxlength="30" style="font-weight:bold;width:240px;height:25px" />
					<h:outputText value=""></h:outputText>
					<h:message for="numOfTweets" style="color:red;font-weight:bold" />
					<h:outputText value=""></h:outputText>
					<h:commandButton type="submit" value="Generate Heat Map"
						action="#{streamingHeatMap.entry}"
						style="font-weight:bold;width:250px;height:30px" />
					<h:outputText value=""></h:outputText>
					<h:commandButton type="submit" value="Find Sentiment"
						action="#{launchSentiment.findSentiment}"
						style="font-weight:bold;width:250px;height:30px" />
				</h:panelGrid>
				<hr>
				<hr>
				<h:graphicImage rendered="#{launchSentiment.showPieChart}"
					value="images/PieChart.jpeg" height="336" width="448" />
			</div>
		</h:form>
	</f:view>
</body>
</html>