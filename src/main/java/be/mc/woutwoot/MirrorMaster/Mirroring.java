package be.mc.woutwoot.MirrorMaster;

import be.mc.woutwoot.MirrorMaster.objects.User;

public interface Mirroring {

    void Stairs(User user);

    void ButtonLevers(User user);

    void Torches(User user);

    void Halfslabs(User user);

    void Doors(User user);

    void Fences(User user);

    void Gates(User user);

    void Default(User user);

    void Remove(User user);
}
