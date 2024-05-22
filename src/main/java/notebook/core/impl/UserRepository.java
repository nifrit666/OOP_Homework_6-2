package notebook.core.impl;

import notebook.core.mapper.impl.UserMapper;
import notebook.repository.model.User;
import notebook.repository.DBConnector;
import notebook.core.GBRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements GBRepository<User, Long> {
    private final UserMapper mapper;
    private final DBConnector dbName;

    public UserRepository(DBConnector dbName) {
        this.mapper = new UserMapper();
        this.dbName = dbName;
    }

    @Override
    public List<User> findAll() {
        List<String> lines = dbName.readAll();
        List<User> users = new ArrayList<>();
        for (String line : lines) {
            users.add(mapper.toOutput(line));
        }
        return users;
    }

    @Override
    public User create(User user) {
        List<User> users = findAll();
        long max = 0L;
        for (User u : users) {
            long id = u.getId();
            if (max < id){
                max = id;
            }
        }
        long next = max + 1;
        user.setId(next);
        users.add(user);
        saveAll(users);
        return user;
    }

    @Override
    public User findById(Long id) {
        List <User> users = findAll();
        for (User user : users)
            if (user.getId().equals(id)) {
                return user;
            }
        return null;
    }
    @Override
    public Optional<User> update(Long id, User user) {
        List <User> users = findAll();
        User editUser = users.stream()
                .filter(u -> u.getId()
                        .equals(id))
                .findFirst().orElseThrow(() -> new RuntimeException("User not found"));
        editUser.setId(id);
        editUser.setFirstName(user.getFirstName());
        editUser.setPhone(user.getPhone());
        editUser.setLastName(user.getLastName());
        saveAll(users);
        return Optional.empty();
    }
    @Override
    public boolean delete(Long id) {
        try{
            List <User> users = findAll();
            for (int i = users.size()-1; i >= 0; i--){
                if (users.get(i).getId().equals(id)){
                    users.remove(i);
                    saveAll(users);
                    return true;
                }
            }
        }catch (Exception e){
            System.out.printf("Пользователь с идентификатором %s не найден\n", id);
        }
        return false;
    }
    private void saveAll(List<User> users) {
        List<String> lines = new ArrayList<>();
        for (User u: users) {
            lines.add(mapper.toInput(u));
        }
        if (!lines.isEmpty()){
            dbName.saveAll(lines);
        }
    }
}
