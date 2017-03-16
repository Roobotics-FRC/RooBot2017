package org.usfirst.frc.team4373.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.commands.auton.PositionBasedGearAuton;
import org.usfirst.frc.team4373.robot.commands.auton.TestAuton;
import org.usfirst.frc.team4373.robot.commands.auton.TimeBasedAuton;
import org.usfirst.frc.team4373.robot.commands.auton.TimeBasedGearAuton;
//import org.usfirst.frc.team4373.robot.commands.teleop.DriveWithJoystick;
import org.usfirst.frc.team4373.robot.subsystems.Climber;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4373.robot.subsystems.GearRelease;

/**
 * This is the main robot class.
 */
public class Robot extends IterativeRobot {
    private Command autonCommand = null;
    private SendableChooser autonChooser = null;

    @Override
    public void robotInit() {
        SmartDashboard.putNumber("Auton Time:", 4);
        SmartDashboard.putNumber("Auton Revolutions:", 10);
        SmartDashboard.putNumber("Auton Speed:", 0.5);

        autonChooser = new SendableChooser();
        autonChooser.addObject("Disabled", "disabled");
        autonChooser.addDefault("DriveStraight", "driveStraight");
        autonChooser.addObject("DriveRevolutions", "driveRevolutions");
        autonChooser.addObject("RudimentaryGear", "rudimentaryGear");
        autonChooser.addObject("Test", "test");
        SmartDashboard.putData("Auton Mode Selector", autonChooser);

        // OI.getOI().getGyro().calibrate();
        DriveTrain.getDriveTrain();
        Climber.getClimber();
        GearRelease.getGearRelease();
    }

    @Override
    public void teleopInit() {
        if (autonCommand != null) {
            autonCommand.cancel();
        }
        OI.getOI().getGyro().reset();
        super.teleopInit();
    }

    @Override
    public void autonomousInit() {
        //DriveWithJoystick.getDriveWithJoystick().cancel();
        if (autonCommand != null) {
            autonCommand.cancel();
        }
        String command = (String) autonChooser.getSelected();
        int autonValueKey = (int) SmartDashboard.getNumber("Auton Time:",
                RobotMap.TIME_BASED_AUTON_DEFAULT_SECONDS);
        int autonRevsKey = (int) SmartDashboard.getNumber("Auton Revolutions:",
                RobotMap.POSITION_BASED_AUTON_DEFAULT_REVOLUTIONS);
        double motorValue = SmartDashboard.getNumber("Auton Speed:",
                RobotMap.TIME_BASED_AUTON_MOTOR_VALUE);
        switch (command) {
            case "driveStraight":
                autonCommand = TimeBasedAuton.getTimeBasedAuton(autonValueKey, motorValue);
                break;
            case "rudimentaryGear":
                autonCommand = TimeBasedGearAuton.getTimeBasedGearAuton(autonValueKey, motorValue);
                break;
            case "driveRevolutions":
                autonCommand = PositionBasedGearAuton.getPositionBasedGearAUton(
                        autonRevsKey, motorValue);
                break;
            case "test":
                autonCommand = TestAuton.getTestAuton();
                break;
            default:
                autonCommand = null;
        }
        if (autonCommand != null) {
            autonCommand.start();
        }
    }

    @Override
    public void autonomousPeriodic() {
        //DriveWithJoystick.getDriveWithJoystick().cancel();
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopPeriodic() {
        if (autonCommand != null) {
            autonCommand.cancel();
        }
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("Gyro", OI.getOI().getGyro().getAngle());
    }

    public String toString() {
        return "Main robot class";
    }
}
// "A radioactive cat has eighteen half-lives."
// A friend is a present you give yourself.
		-- Robert Louis Stevenson
// Good day to avoid cops.  Crawl to work.
// Is not marriage an open question, when it is alleged, from the
beginning of the world, that such as are in the institution wish to get
out, and such as are out wish to get in?
		-- Ralph Emerson
// I only know what I read in the papers.
		-- Will Rogers
// All we know is the phenomenon: we spend our time sending messages to each
other, talking and trying to listen at the same time, exchanging information.
This seems to be our most urgent biological function; it is what we do with
our lives."
		-- Lewis Thomas, "The Lives of a Cell"
// Two peanuts were walking through the New York.  One was assaulted.
// The true way goes over a rope which is not stretched at any great height
but just above the ground.  It seems more designed to make people stumble
than to be walked upon.
		-- Franz Kafka
// People who are funny and smart and return phone calls get much better
press than people who are just funny and smart.
		-- Howard Simons, "The Washington Post"
// No problem is insoluble in all conceivable circumstances.
// I think my career is ruined!
// Q:	Why did the astrophysicist order three hamburgers?
A:	Because he was hungry.
// If you have a procedure with 10 parameters, you probably missed some.
// People are beginning to notice you.  Try dressing before you leave the house.
// Counting in octal is just like counting in decimal--if you don't use your thumbs.
		-- Tom Lehrer
// All that glitters has a high refractive index.
// QOTD:
	"I used to go to UCLA, but then my Dad got a job."
// A neighbor came to Nasrudin, asking to borrow his donkey.  "It is out on
loan," the teacher replied.  At that moment, the donkey brayed loudly inside
the stable.  "But I can hear it bray, over there."  "Whom do you believe,"
asked Nasrudin, "me or a donkey?"
// You may call me by my name, Wirth, or by my value, Worth.
- Nicklaus Wirth
// I know you believe you understand what you think this fortune says, but
I'm not sure you realize that what you are reading is not what it means.
// The fact that boys are allowed to exist at all is evidence of a remarkable
Christian forbearance among men.
		-- Ambrose Bierce
// Be not anxious about what you have, but about what you are.
		-- Pope St. Gregory I
// Today is what happened to yesterday.
// Coming to Stores Near You:

101 Grammatically Correct Popular Tunes Featuring:

	(You Aren't Anything but a) Hound Dog
	It Doesn't Mean a Thing If It Hasn't Got That Swing
	I'm Not Misbehaving

And A Whole Lot More...
// I'd love to kiss you, but I just washed my hair.
		-- Bette Davis, "Cabin in the Cotton"
// ...I don't care for the term 'mechanistic'. The word 'cybernetic' is a lot
more apropos. The mechanistic world-view is falling further and further behind
the real world where even simple systems can produce the most marvellous
chaos. 
-- Peter da Silva
// One learns to itch where one can scratch.
		-- Ernest Bramah
// A Linux machine! because a 486 is a terrible thing to waste!
(By jjs@wintermute.ucr.edu, Joe Sloan)
// The worst cliques are those which consist of one man.
		-- G.B. Shaw
// The voluptuous blond was chatting with her handsome escort in a posh 
restaurant when their waiter, stumbling as he brought their drinks, 
dumped a martini on the rocks down the back of the blonde's dress.  She
sprang to her feet with a wild rebel yell, dashed wildly around the table,
then galloped wriggling from the room followed by her distraught boyfriend.
A man seated on the other side of the room with a date of his own beckoned
to the waiter and said, "We'll have two of whatever she was drinking."
// Life in this society being, at best, an utter bore and no aspect of society
being at all relevant to women, there remains to civic-minded responsible
thrill-seeking females only to overthrow the government, eliminate the money
system, institute complete automation and destroy the male sex.
		-- Valerie Solanas
// Some changes are so slow, you don't notice them.  Others are so fast,
they don't notice you.
// Life is what happens to you while you're busy making other plans.
		-- John Lennon, "Beautiful Boy"
// Thus spake the master programmer:
	"When you have learned to snatch the error code from
	the trap frame, it will be time for you to leave."
		-- Geoffrey James, "The Tao of Programming"
// You say you are lying.  But if everything you say is a lie, then you are
telling the truth.  You cannot tell the truth because everything you say
is a lie.  You lie, you tell the truth ... but you cannot, for you lie.
		-- Norman the android, "I, Mudd", stardate 4513.3
// Not all who own a harp are harpers.
		-- Marcus Terentius Varro
// Three may keep a secret, if two of them are dead.
		-- Benjamin Franklin
// Life is a serious burden, which no thinking, humane person would
wantonly inflict on someone else.
		-- Clarence Darrow
// Around the turn of this century, a composer named Camille Saint-Saens wrote
a satirical zoological-fantasy called "Le Carnaval des Animaux."  Aside from
one movement of this piece, "The Swan", Saint-Saens didn't allow this work
to be published or even performed until a year had elapsed after his death.
(He died in 1921.)
	Most of us know the "Swan" movement rather well, with its smooth,
flowing cello melody against a calm background; but I've been having this
fantasy...
	What if he had written this piece with lyrics, as a song to be sung?
And, further, what if he had accompanied this song with a musical saw?  (This
instrument really does exist, often played by percussionists!)  Then the
piece would be better known as:
	SAINT-SAENS' SAW SONG "SWAN"!
// Stability itself is nothing else than a more sluggish motion.
// like:
	When being alive at the same time is a wonderful coincidence.
// Politics and the fate of mankind are formed by men without ideals and without
greatness.  Those who have greatness within them do not go in for politics.
		-- Albert Camus
// Never go to bed mad.  Stay up and fight.
		-- Phyllis Diller, "Phyllis Diller's Housekeeping Hints"
// Perhaps the biggest disappointments were the ones you expected anyway.
// The three biggest software lies:
	(1) *Of course* we'll give you a copy of the source.
	(2) *Of course* the third party vendor we bought that from
	    will fix the microcode.
	(3) Beta test site?  No, *of course* you're not a beta test site.
// Linus:	Hi!  I thought it was you.
	I've been watching you from way off...  You're looking great!
Snoopy:	That's nice to know.
	The secret of life is to look good at a distance.
// The system was down for backups from 5am to 10am last Saturday.
// To a Californian, a person must prove himself criminally insane before he
is allowed to drive a taxi in New York.  For New York cabbies, honesty and
stopping at red lights are both optional.
	-- From "East vs. West: The War Between the Coasts
// "...[Linux's] capacity to talk via any medium except smoke signals."
(By Dr. Greg Wettstein, Roger Maris Cancer Center)
// How come only your friends step on your new white sneakers?
// You too can wear a nose mitten.
// DIDI ... is that a MARTIAN name, or, are we in ISRAEL?
// Mundus vult decipi decipiatur ergo.
		-- Xaviera Hollander
	[The world wants to be cheated, so cheat.]
// Some people live life in the fast lane.  You're in oncoming traffic.
// This thing all things devours:
Birds, beasts, trees, flowers;
Gnaws iron, bites steel;
Grinds hard stones to meal;
Slays king, ruins town,
And beats high mountain down.
