package be.mc.woutwoot.MirrorMaster.mirrors;

import be.mc.woutwoot.MirrorMaster.objects.User;

public interface Mirroring {

    void Stairs(User user);

    void ButtonLevers(User user);

    void Torches(User user);

    void Halfslabs(User user);

    void Doors(User user);

    void Fences(User user);

    void Gates(User user);

    void Trapdoors(User user);

    void Pistons(User user);

    void EndRods(User user);

    void Chests(User user);

    void RotateXZ(User user);

    void Vines(User user);

    void GlassPanes(User user);

    void Default(User user);

    void Remove(User user);
}
