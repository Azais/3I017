package bd;
import java.net.UnknownHostException;

import com.mongodb.*;
public class RechercheMapReduce {
	public static void indexation() throws UnknownHostException, MongoException{
		String mapDF="function map(){var text=this.comment;var words=text.match(/\\w+/g);var df=[];for (w in words){if(df(words[w])==null){df(words[w])=1;	};}for(w in df){emit(w,{df:1})}}";
		String reduceDF="function reduce(key, values){	var total=0;	for(i in values){		total+=values[i].df;	}	return({word.key, df:total});}";
		
		String mapTF="function mapTF(){	var text=this.comment;	var words=text.match(/\\w+/g);	var tf=[];	for(i in words){if(tf[words[i]]==null){	tf[words[i]]=1;}else{tf[words[i]]++;}}for(w in tf){emit(this._id);}}";
		String reduceTF="function reduceTF(key, value){	return({doc: key, tfs: value});}";
		
		Mongo m=new Mongo(DBStatic.mango_host, DBStatic.mango_port);
		DB db=m.getDB("gr2_foufa_keraro");
		DBCollection collection=db.getCollection("comments");
		MapReduceCommand cmd=new MapReduceCommand(collection, mapDF, reduceDF, null, MapReduceCommand.OutputType.INLINE, null);
		MapReduceOutput out=collection.mapReduce(cmd);
		for(DBObject obj: out.results()){
			System.out.println(obj.toString());
		}
	}
}