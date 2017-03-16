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
// You're at Witt's End.
// A CONS is an object which cares.
		-- Bernie Greenberg.
// You can do more with a kind word and a gun than with just a kind word.
- Al Capone
// It is surely a great calamity for a human being to have no obsessions.
- Robert Bly
// Friends may come and go, but enemies accumulate.
		-- Thomas Jones
// (null cookie; hope that's ok)
// Wilner's Observation:
	All conversations with a potato should be conducted in private.
// As I argued in "Beloved Son", a book about my son Brian and the subject
of religious communes and cults, one result of proper early instruction
in the methods of rational thought will be to make sudden mindless
conversions -- to anything -- less likely.  Brian now realizes this and
has, after eleven years, left the sect he was associated with.  The 
problem is that once the untrained mind has made a formal commitment to
a religious philosophy -- and it does not matter whether that philosophy
is generally reasonable and high-minded or utterly bizarre and 
irrational -- the powers of reason are suprisingly ineffective in 
changing the believer's mind.
- Steve Allen, comdeian, from an essay in the book "The Courage of 
  Conviction", edited by Philip Berman
// 			HOW TO PROVE IT, PART 2

proof by cumbersome notation:
	Best done with access to at least four alphabets and special
	symbols.

proof by exhaustion:
	An issue or two of a journal devoted to your proof is useful.

proof by omission:
	'The reader may easily supply the details'
	'The other 253 cases are analogous'
	'...' 
// When all else fails, read the instructions.
// Life is like a sewer.  What you get out of it depends on what you put into it.
		-- Tom Lehrer
// "Beware of the man who works hard to learn something, learns it, and
finds himself no wiser than before," Bokonon tells us.  "He is full of
murderous resentment of people who are ignorant without having come by
their ignorance the hard way."
		-- Kurt Vonnegut, "Cat's Cradle"
// Greatness is a transitory experience. It is never consistent.
// 	How do you insult a lawyer?
	You might as well not even try.  Consider: of all the highly
trained and educated professions, law is the only one in which the prime
lesson is that *winning* is more important than *truth*.
	Once someone has sunk to that level, what worse can you say about them?
// My doctor told me to stop having intimate dinners for four.  Unless there
are three other people.
		-- Orson Welles
// There will always be beer cans rolling on the floor of your car when
the boss asks for a lift home from the office.
// 	Plumbing is one of the easier of do-it-yourself activities,
requiring only a few simple tools and a willingness to stick your arm into a
clogged toilet.  In fact, you can solve many home plumbing problems, such as
annoying faucet drip, merely by turning up the radio.  But before we get
into specific techniques, let's look at how plumbing works.
	A plumbing system is very much like your electrical system, except
that instead of electricity, it has water, and instead of wires, it has
pipes, and instead of radios and waffle irons, it has faucets and toilets.
So the truth is that your plumbing systems is nothing at all like your
electrical system, which is good, because electricity can kill you.
		-- Dave Barry, "The Taming of the Screw"
// I can't drive 55.
I'm looking forward to not being able to drive 65, either.
// Avoid cliches like the plague.  They're a dime a dozen.
// No small art is it to sleep: it is necessary for that purpose to keep
awake all day.
		-- Nietzsche
// O slender as a willow-wand!  O clearer than clear water!
O reed by the living pool!  Fair river-daughter!
O spring-time and summer-time, and spring again after!
O wind on the waterfall, and the leaves' laughter!
		-- J. R. R. Tolkien
// SHOP OR DIE, people of Earth!
[offer void where prohibited]
-- Capitalists from outer space, from Justice League Int'l comics
// If elected, Zippy pledges to each and every American a 55-year-old houseboy ...
// Q:	Why is Poland just like the United States?
A:	In the United States you can't buy anything for zlotys and in
	Poland you can't either, while in the U.S. you can get whatever
	you want for dollars, just as you can in Poland.
		-- being told in Poland, 1987
// One thing about the past.
It's likely to last.
		-- Ogden Nash
// Whenever a system becomes completely defined, some damn fool discovers
something which either abolishes the system or expands it beyond recognition.
// Why not have an old-fashioned Christmas for your family this year? Just
picture the scene in your living room on Christmas morning as your children
open their old-fashioned presents.

Your 11-year-old son: "What the heck is this?"

You:	"A spinning top!  You spin it around, and then eventually it falls
down.  What fun!  Ha, ha!"

Son:	"Is this a joke?  Jason Thompson's parents got him a computer with 
two disk drives and 128 kilobytes of random-access memory, and I get this 
cretin TOP?"

Your 8-year-old daughter: "You think that's bad?  Look at this."

You:	"It's figgy pudding!  What a treat!"

Daughter: "It looks like goat barf."
		-- Dave Barry, "Simple, Homespun Gifts"
// In order to get a loan you must first prove you don't need it.
// "Ninety percent of baseball is half mental."
-- Yogi Berra
// It has long been an axiom of mine that the little things are infinitely
the most important.
		-- Sir Arthur Conan Doyle, "A Case of Identity"
// Wedding, n:
	A ceremony at which two persons undertake to become one, one undertakes
	to become nothing and nothing undertakes to become supportable.
		-- Ambrose Bierce
// The most popular labor-saving device today is still a husband with money.
		-- Joey Adams, "Cindy and I"
// Unix is a lot more complicated (than CP/M) of course -- the typical Unix
hacker can never remember what the PRINT command is called this week --
but when it gets right down to it, Unix is a glorified video game.
People don't do serious work on Unix systems; they send jokes around the
world on USENET or write adventure games and research papers.
		-- E. Post
		"Real Programmers Don't Use Pascal", Datamation, 7/83
// 	THE LESSER-KNOWN PROGRAMMING LANGUAGES #10: SIMPLE

SIMPLE is an acronym for Sheer Idiot's Monopurpose Programming Language
Environment.  This language, developed at the Hanover College for
Technological Misfits, was designed to make it impossible to write code
with errors in it.  The statements are, therefore, confined to BEGIN,
END and STOP.  No matter how you arrange the statements, you can't make
a syntax error.  Programs written in SIMPLE do nothing useful.  Thus
they achieve the results of programs written in other languages without
the tedious, frustrating process of testing and debugging.
// Rattling around the back of my head is a disturbing image of something I
saw at the airport ... Now I'm remembering, those giant piles of computer
magazines right next to "People" and "Time" in the airport store.  Does
it bother anyone else that half the world is being told all of our hard-won
secrets of computer technology?  Remember how all the lawyers cried foul
when "How to Avoid Probate" was published?  Are they taking no-fault
insurance lying down?  No way!  But at the current rate it won't be long
before there are stacks of the "Transactions on Information Theory" at the
A&P checkout counters.  Who's going to be impressed with us electrical
engineers then?  Are we, as the saying goes, giving away the store?
		-- Robert W. Lucky, IEEE President
// The world is coming to an end!  Repent and return those library books!
// Democracy is a process by which the people are free to choose the man who
will get the blame.
		-- Laurence J. Peter
// Now I am depressed ...
// The evolution of the human race will not be accomplished in the ten thousand
years of tame animals, but in the million years of wild animals, because man
is and will always be a wild animal.
-- Charles Galton Darwin
// 101 USES FOR A DEAD MICROPROCESSOR
	(1)  Scarecrow for centipedes
	(2)  Dead cat brush
	(3)  Hair barrettes
	(4)  Cleats
	(5)  Self-piercing earrings
	(6)  Fungus trellis
	(7)  False eyelashes
	(8)  Prosthetic dog claws
        .
        .
        .
	(99)  Window garden harrow (pulled behind Tonka tractors)
	(100) Killer velcro
	(101) Currency
// Xerox your lunch and file it under "sex offenders"!
// I have to think hard to name an interesting man who does not drink.
		-- Richard Burton
// What's the matter with the world?  Why, there ain't but one thing wrong
with every one of us -- and that's "selfishness."
		-- The Best of Will Rogers
// To stay young requires unceasing cultivation of the ability to unlearn
old falsehoods.
		-- Lazarus Long, "Time Enough For Love"
// Alexander Hamilton started the U.S. Treasury with nothing - and that was
the closest our country has ever been to being even.
	-- The Best of Will Rogers
// The groundhog is like most other prophets; it delivers its message and then
disappears.
// Even the best of friends cannot attend each other's funeral.
		-- Kehlog Albran, "The Profit"
// If I'm over the hill, why is it I don't recall ever being on top?
		-- Jerry Muscha
// Certain old men prefer to rise at dawn, taking a cold bath and a long
walk with an empty stomach and otherwise mortifying the flesh.  They
then point with pride to these practices as the cause of their sturdy
health and ripe years; the truth being that they are hearty and old,
not because of their habits, but in spite of them.  The reason we find
only robust persons doing this thing is that it has killed all the
others who have tried it.
		-- Ambrose Bierce, "The Devil's Dictionary"
// There's too much beauty upon this earth for lonely men to bear.
		-- Richard Le Gallienne
// Armenians and Azerbaijanis in Stepanakert, capital of the Nagorno-Karabakh
autonomous region, rioted over much needed spelling reform in the Soviet Union.
		-- P.J. O'Rourke
// What makes you think graduate school is supposed to be satisfying?
		-- Erica Jong, "Fear of Flying"
// An Italian is COMBING his hair in suburban DES MOINES!
// May your SO always know when you need a hug.
// First Law of Socio-Genetics:
	Celibacy is not hereditary.
// Your love life will be... interesting.
// What sane person could live in this world and not be crazy?
		-- Ursula K. LeGuin
// The primary requisite for any new tax law is for it to exempt enough
voters to win the next election.
// Violence is a sword that has no handle -- you have to hold the blade.
// Why bother building any more nuclear warheads until we use the ones we have?
// pixel, n.:
	A mischievous, magical spirit associated with screen displays.
	The computer industry has frequently borrowed from mythology:
	Witness the sprites in computer graphics, the demons in artificial
	intelligence, and the trolls in the marketing department.
// Although written many years ago, Lady Chatterley's Lover has just been
reissued by the Grove Press, and this pictorial account of the
day-to-day life of an English gamekeeper is full of considerable
interest to outdoor minded readers, as it contains many passages on
pheasant-raising, the apprehending of poachers, ways to control vermin,
and other chores and duties of the professional gamekeeper.
Unfortunately, one is obliged to wade through many pages of extraneous
material in order to discover and savour those sidelights on the
management of a midland shooting estate, and in this reviewer's opinion
the book cannot take the place of J. R. Miller's "Practical Gamekeeping."
		-- Ed Zern, "Field and Stream" (Nov. 1959)
// The first marriage is the triumph of imagination over intelligence,
and the second the triumph of hope over experience.
// If the colleges were better, if they really had it, you would need to get
the police at the gates to keep order in the inrushing multitude.  See in
college how we thwart the natural love of learning by leaving the natural
method of teaching what each wishes to learn, and insisting that you shall
learn what you have no taste or capacity for.  The college, which should
be a place of delightful labor, is made odious and unhealthy, and the
young men are tempted to frivolous amusements to rally their jaded spirits.
I would have the studies elective.  Scholarship is to be created not
by compulsion, but by awakening a pure interest in knowledge.  The wise
instructor accomplishes this by opening to his pupils precisely the
attractions the study has for himself.  The marking is a system for schools,
not for the college; for boys, not for men; and it is an ungracious work to
put on a professor.
		-- Ralph Waldo Emerson
// Who messed with my anti-paranoia shot?
// I'll meet you... on the dark side of the moon...
		-- Pink Floyd
// Pohl's law:
	Nothing is so good that somebody, somewhere, will not hate it.
// After any salary raise, you will have less money at the end of the
month than you did before.
// As to Jesus of Nazareth...I think the system of Morals and his Religion,
as he left them to us, the best the World ever saw or is likely to see;
but I apprehend it has received various corrupting Changes, and I have,
with most of the present Dissenters in England, some doubts as to his
divinity.
- Benjamin Franklin
// Question authority.
// As of next week, passwords will be entered in Morse code.
// XLVII:
	Two-thirds of the Earth's surface is covered with water.  The other
	third is covered with auditors from headquarters.
XLVIII:
	The more time you spend talking about what you have been doing, the
	less time you have to spend doing what you have been talking about.
	Eventually, you spend more and more time talking about less and less
	until finally you spend all your time talking about nothing.
XLIX:
	Regulations grow at the same rate as weeds.
L:
	The average regulation has a life span one-fifth as long as a
	chimpanzee's and one-tenth as long as a human's -- but four times
	as long as the official's who created it.
LI:
	By the time of the United States Tricentennial, there will be more
	government workers than there are workers.
LII:
	People working in the private sector should try to save money.
	There remains the possibility that it may someday be valuable again.
		-- Norman Augustine
// Keep a diary and one day it'll keep you.
		-- Mae West
// Executive ability is deciding quickly and getting somebody else to do
the work.
		-- John G. Pollard
// Grandpa Charnock's Law:
	You never really learn to swear until you learn to drive.

	[I thought it was when your kids learned to drive.  Ed.]
// The happiest time of a person's life is after his first divorce.
		-- J.K. Galbraith 
// The Gurus of Unix Meeting of Minds (GUMM) takes place Wednesday, April
1, 2076 (check THAT in your perpetual calendar program), 14 feet above
the ground directly in front of the Milpitas Gumps.  Members will grep
each other by the hand (after intro), yacc a lot, smoke filtered
chroots in pipes, chown with forks, use the wc (unless uuclean), fseek
nice zombie processes, strip, and sleep, but not, we hope, od.  Three
days will be devoted to discussion of the ramifications of whodo.  Two
seconds have been allotted for a complete rundown of all the user-
friendly features of Unix.  Seminars include "Everything You Know is
Wrong", led by Tom Kempson, "Batman or Cat:man?" led by Richie Dennis
"cc C?  Si!  Si!" led by Kerwin Bernighan, and "Document Unix, Are You
Kidding?" led by Jan Yeats.  No Reader Service No. is necessary because
all GUGUs (Gurus of Unix Group of Users) already know everything we
could tell them.
		-- "Get GUMMed," Dr. Dobb's Journal, June '84
// I think there's a world market for about five computers.
		-- attr. Thomas J. Watson (Chairman of the Board, IBM), 1943
// Did you know that clones never use mirrors?
		-- Ambrose Bierce, "The Devil's Dictionary"
// Be careful!  Is it classified?
// With a rubber duck, one's never alone.
		-- "The Hitchhiker's Guide to the Galaxy"
// On Thanksgiving Day all over America, families sit down to dinner at the
same moment -- halftime.
// With a rubber duck, one's never alone.
		-- "The Hitchhiker's Guide to the Galaxy"
// There are no manifestos like cannon and musketry.
		-- The Duke of Wellington
// Nobody knows what goes between his cold toes and his warm ears.
		-- Roy Harper
// "If it ain't broke, don't fix it."
- Bert Lantz
// You will be advanced socially, without any special effort on your part.
// 	The General disliked trying to explain the highly technical inner
workings of the U.S. Air Force.
	"$7,662 for a ten cup coffee maker, General?" the Senator asked.
	In his head he ran through his standard explanations.  "It's not so,"
he thought.  "It's a deterrent."  Soon he came up with, "It's computerized,
Senator.  Tiny computer chips make coffee that's smooth and full-bodied.  Try
a cup."
	The Senator did.  "Pfffttt!  Tastes like jet fuel!"
	"It's not so," the General thought.  "It's a deterrent."
	Then he remembered something.  "We bought a lot of untested computer
chips," the General answered.  "They got into everything.  Just a little
mix-up.  Nothing serious."
	Then he remembered something else.  It was at the site of the
mysterious B-1 crash.  A strange smell in the fuel lines.  It smelled like
coffee.  Smooth and full bodied...
		-- Another Episode of General's Hospital
// Life is the living you do, Death is the living you don't do.
		-- Joseph Pintauro
// "Cogito ergo I'm right and you're wrong."
		-- Blair Houghton
// I would be batting the big feller if they wasn't ready with the other one,
but a left-hander would be the thing if they wouldn't have knowed it already
because there is more things involved than could come up on the road, even
after we've been home a long while.
		-- Casey Stengel
// If God had intended Man to Smoke, He would have set him on Fire.
// Good government never depends upon laws, but upon the personal qualities of
those who govern.  The machinery of government is always subordinate to the
will of those who administer that machinery.  The most important element of
government, therefore, is the method of choosing leaders.
		-- Frank Herbert, "Children of Dune"
// Vulcans do not approve of violence.
		-- Spock, "Journey to Babel", stardate 3842.4
// I read Playboy for the same reason I read National Geographic.  To see
the sights I'm never going to visit.
// Everything takes longer, costs more, and is less useful.
		-- Erwin Tomash
// [Maturity consists in the discovery that] there comes a critical moment
where everything is reversed, after which the point becomes to understand
more and more that there is something which cannot be understood.
		-- S. Kierkegaard
// 	"These are DARK TIMES for all mankind's HIGHEST VALUES!"
	"These are DARK TIMES for FREEDOM and PROSPERITY!"
	"These are GREAT TIMES to put your money on BAD GUY to kick the CRAP
out of MEGATON MAN!"
// The final delusion is the belief that one has lost all delusions.
		-- Maurice Chapelain, "Main courante"
// Give them RADAR-GUIDED SKEE-BALL LANES and VELVEETA BURRITOS!!
// Scrubbing floors and emptying bedpans has as much dignity as the Presidency.
		-- Richard Nixon
// You had mail.  Paul read it, so ask him what it said.
// Uncertain fortune is thoroughly mastered by the equity of the calculation.
- Blaise Pascal
