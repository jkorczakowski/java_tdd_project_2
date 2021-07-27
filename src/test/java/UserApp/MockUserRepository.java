package UserApp;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;

public class MockUserRepository implements UserRepository {

    private Set<User> db;

    public MockUserRepository() {
        this.db = new HashSet<>();
    }

    @Override
    public <S extends User> S save(S s) {
        if (s == null) throw new IllegalArgumentException("ERROR: User cannot be null");
        db.add(s);
        return s;
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> iterable) {
        boolean nullCheck = StreamSupport.stream(iterable.spliterator(), false).anyMatch(Objects::isNull);
        if (nullCheck) throw new IllegalArgumentException("ERROR: User cannot be null");
        iterable.iterator().forEachRemaining(db::add);
        return iterable;
    }

    @Override
    public Optional<User> findById(Long l) {
        if(l == null) throw new IllegalArgumentException("ERROR: Id cannot be null");
        return db.stream().filter(user -> user.getId().equals(l)).findFirst();
    }

    @Override
    public boolean existsById(Long l) {
        if(l == null) throw new IllegalArgumentException("ERROR: Id cannot be null");
        return db.stream().anyMatch(user -> user.getId().equals(l));
    }

    @Override
    public Iterable<User> findAll() {
        return db;
    }

    @Override
    public Iterable<User> findAllById(Iterable<Long> iterable) {
        boolean nullCheck = StreamSupport.stream(iterable.spliterator(),false)
                .anyMatch(Objects::isNull);
        if(nullCheck) throw new IllegalArgumentException("ERROR: Ids cannot be null");
        Iterator<Long> longIterator = iterable.iterator();
        Set<User> result = new HashSet<>();
        while(longIterator.hasNext()){
            Long currentId = longIterator.next();
            if(currentId==null) throw new IllegalArgumentException("ERROR: Ids cannot be null");
            this.findById(currentId).ifPresent(result::add);
        }
        return result;
    }

    @Override
    public long count() { 
    
        return db.size();
    }

    @Override
    public void deleteById(Long l) {
        if(l == null) throw new IllegalArgumentException("ERROR: Id cannot be null");
        Optional<User> itemToRemove = this.findById(l);
        itemToRemove.ifPresent(entity -> {
            if(!db.contains(entity)) throw new IllegalArgumentException("ERROR: User does not exists");
            db.remove(entity);
        });
    }

    @Override
    public void delete(User user) {
        if(user == null) throw new IllegalArgumentException("ERROR: User cannot be null");
        Optional<User> itemToRemove = Optional.of(user);
        itemToRemove.ifPresent(entity -> {
            if(!db.contains(entity)) throw new IllegalArgumentException("ERROR: User does not exists");
            db.remove(entity);
        });
    }

    @Override
    public void deleteAll(Iterable<? extends User> iterable) {
        Iterator<? extends User> userIterator = iterable.iterator();
        while(userIterator.hasNext()){
            User currentUser = userIterator.next();
            if(currentUser == null) throw new IllegalArgumentException("ERROR: User cannot be null");
            this.delete(currentUser);
        }
    }

    @Override
    public void deleteAll() {
        db.forEach(db::remove);
    }

    @Override
    public Set<User> findUsersByName(String name) {
        if(name == null) throw new IllegalArgumentException("ERROR: Name cannot be null");
        Set<User> result = new HashSet<>();
        db.stream().filter(user -> user.getName().equals(name)).findFirst();
        return result;
    }

    @Override
    public Set<User> findUsersByNameContaining(String keyword) {
        if(keyword == null) throw new IllegalArgumentException("ERROR: Keyword cannot be null");
        Set<User> result = new HashSet<>();
        db.stream().filter(user -> user.getName().contains(keyword)).forEach(result::add);
        return result;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        if(email == null) throw new IllegalArgumentException("ERROR: Email cannot be null");
        return db.stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }

    @Override
    public Set<User> findUsersByEmailContaining(String keyword) {
        if(keyword == null) throw new IllegalArgumentException("ERROR: Keyword cannot be null");
        Set<User> result = new HashSet<>();
        db.stream().filter(user -> user.getEmail().contains(keyword)).forEach(result::add);
        return result;
    }

    @Override
    public void editUser(User oldUser, User newUser) {
        if(newUser == null || oldUser == null) throw new IllegalArgumentException("ERROR: OLD || NEW user cannot be null");
        db.remove(oldUser);
        db.add(newUser);
        
    }
    
}