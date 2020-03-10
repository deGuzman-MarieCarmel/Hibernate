package movieHibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class mainApp {
    private List<actors> actors;
    private hibernateConfig theHibernateUtility;
    private String actor_name;
    private String actor_name2;
    private String movie_name;

    public mainApp(){
        theHibernateUtility = new hibernateConfig();
    }

    public static void main(String[] args){
        mainApp myHibernateRunner = new mainApp();
        System.out.println("\n\nAdding New Actor");
        myHibernateRunner.addNewActor("Chris Evans");
        myHibernateRunner.addNewActor("Robert Downey Jr.");
        System.out.println("\n\nShowing All Actors");
        myHibernateRunner.showAllActors();
        System.out.println("\n\nModifying an Actor");
        myHibernateRunner.modifyActor("Chris Evans", "Chris Hemsworth");
        System.out.println("\n\nAdding a Movie Name");
        myHibernateRunner.addMovieName("Robert Downey Jr.","Avengers End Game");
        System.out.println("\n\nDeleting Added Actors");
        myHibernateRunner.deleteAddedActor("Robert Downey Jr.");
        myHibernateRunner.deleteAddedActor("Chris Hemsworth");
        System.out.println("\n\nShowing All Actors");
        myHibernateRunner.showAllActors();
    }

    public void addNewActor(String actor_name) {
        this.actor_name = actor_name;
        Session session = theHibernateUtility.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        actors passedActor = new actors();
        passedActor.setActor_name(actor_name);
        session.save(passedActor);
        transaction.commit();
        System.out.println("Actor " + actor_name + " with generated ID " + passedActor.getActor_id() + " added");
    }

    private void showAllActors() {
        Session session = theHibernateUtility.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query allActorsQuery = session.createQuery("from actors");
        actors = allActorsQuery.list();
        Iterator<actors> iter = actors.iterator();;
        while(iter.hasNext()) {
            actors element = iter.next();
            System.out.println("\u001B[32mData Entry:\u001B[0m " + element.toString() + " \u001B[32m# of Movie Names:\u001B[0m " + element.getMovieNames().size());
        }
        System.out.println("\u001B[33mTotal Actors in Database:\u001B[0m "+ actors.size());
        transaction.commit();
    }

    private void modifyActor(String actor_name, String actor_name2) {
        this.actor_name = actor_name;
        this.actor_name2 = actor_name2;
        Session session = theHibernateUtility.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query singleUserQuery = session.createQuery("from actors a where a.actor_name='" + actor_name + "'");
        actors passedActor = (actors) singleUserQuery.uniqueResult();
        passedActor.setActor_name(actor_name2);
        session.merge(passedActor);
        transaction.commit();
        System.out.println("Actor " + actor_name + " changed to " + actor_name2);
        showAllActors();
    }

    private void addMovieName(String actor_name, String movie_name) {
        this.actor_name = actor_name;
        this.movie_name = movie_name;
        Session session = theHibernateUtility.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query myQuery = session.createQuery("from actors a where a.actor_name='" + actor_name + "'");
        actors passedMovie = (actors) myQuery.uniqueResult();
        movie myMovieName = new movie();
        myMovieName.setMovie_name(movie_name);
        Set<movie> actorMovieName = passedMovie.getMovieNames();
        actorMovieName.add(myMovieName);
        session.save(myMovieName);
        session.merge(passedMovie);
        transaction.commit();
        System.out.println("Movie name " + movie_name + " added to " + actor_name);
        showAllActors();
    }

    private void deleteAddedActor(String actor_name) {
        this.actor_name = actor_name;
        Session session = theHibernateUtility.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Query singleUserQuery = session.createQuery("from actors a where a.actor_name='" + actor_name + "'");
        actors passedActor = (actors)singleUserQuery.uniqueResult();
        session.delete(passedActor);
        transaction.commit();
        System.out.println("Deleted actor " + actor_name);
        //showAllActors();
    }

}
