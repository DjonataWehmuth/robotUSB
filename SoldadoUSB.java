package br.com.djonatawehmuth;

import robocode.*;
import java.awt.*;
import static robocode.util.Utils.normalRelativeAngle;

public class SoldadoUSB extends AdvancedRobot {

    double anguloAnterior = 0;
    int miraErrada = 0;
    double velDevagar = 1;
    double velMedia = 2;
    double velRapido = 3;

    public void run() {
        setAdjustRadarForGunTurn(false);
        setAdjustGunForRobotTurn(true);
        execute();

        while (true) {
            scan();
            setTurnGunRight(10);
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        mira(e.getBearing());
        if (e.getDistance() <= 200) {
            setColors(Color.BLACK, Color.ORANGE, Color.green, Color.black, Color.green);
            if (e.getDistance() < 100) {
                setFire(3);
                setFireBullet(3);
                setAhead(100);
                setMaxVelocity(velRapido);
                setTurnRight(e.getBearing());
                setAhead(e.getDistance() / 3);
            }
            setAhead(100);
            setFire(3);
        } else if (e.getDistance() < 300) {
            setAhead(100);
            setMaxVelocity(velRapido);
            setFire(2);
            setColors(Color.BLUE, Color.ORANGE, Color.yellow, Color.red, Color.yellow);
            setTurnRight(e.getBearing());
            setAhead(e.getDistance() / 2);
        } else {
            setFire(1);
            setColors(Color.green, Color.ORANGE, Color.red, Color.white, Color.red);
            setTurnRight(e.getBearing());
            setAhead(e.getDistance() / 2);
        }
    }

    @Override
    public void onHitWall(HitWallEvent e) {
        setFire(1);
        setTurnRight(180);
    }

    @Override
    public void onHitRobot(HitRobotEvent e) {
        if (e.isMyFault()) {
            setAhead(100);
            setFire(3);
            setFireBullet(1);
            setFire(3);
        }
    }

    @Override
    public void onBulletHit(BulletHitEvent e) {
        setFire(3);
        miraErrada = 0;
    }

    @Override
    public void onHitByBullet(HitByBulletEvent event) {
        setBack(100);
        setTurnRight(20);
        setMaxVelocity(velRapido);

    }

    @Override
    public void onBulletMissed(BulletMissedEvent event) {
        miraErrada++;
        if (miraErrada > 10) {
            stop();
            scan();
            setTurnRight(45);
            setAhead(45);
            setFire(1);
        }
    }

    public void mira(double Adv) {
        double A = getHeading() + Adv - getGunHeading();
        if (!(A > -180 && A <= 180)) {
            while (A <= -180) {
                A += 360;
            }
            while (A > 180) {
                A -= 360;
            }
        }
        turnGunRight(A);
    }
}