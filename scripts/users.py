import random
from dataclasses import dataclass

from model import User, Rating
from write_sql import writeUsers, writeUserRatings

ratings = dict(
  ju={("tt0111161", 1), ("tt0068646", 1), ("tt0071562", 1), ("tt0468569", 1), ("tt0108052", 1), ("tt0110912", 1),
      ("tt0060196", 1), ("tt0109830", 1), ("tt0137523", 1), ("tt0099685", 1), ("tt0114369", 1), ("tt0120815", 1),
      ("tt0064116", 1), ("tt0110413", 1), ("tt0114814", 1), ("tt0407887", 1), ("tt0482571", 1), ("tt0078788", -1),
      ("tt1345836", 1), ("tt1853728", 1), ("tt2380307", 1), ("tt7286456", -1), ("tt0086250", 1), ("tt0169547", 1),
      ("tt0361748", 1), ("tt0059578", 1), ("tt0105695", 1), ("tt0112641", 1), ("tt0113277", 1), ("tt0119488", 1),
      ("tt0120735", 1), ("tt0372784", 1), ("tt0993846", 1), ("tt1130884", 1), ("tt7775622", 1), ("tt0477348", 1),
      ("tt0758758", -1), ("tt1205489", 1), ("tt2119532", 1), ("tt2267998", -1), ("tt0058461", 1), ("tt0072890", 1),
      ("tt0401792", 1), ("tt0454921", 1), ("tt2084970", 1), ("tt2948356", 1), ("tt0304141", 1), ("tt0371746", 1),
      ("tt0061418", 1), ("tt0120338", 1), ("tt0126029", 1), ("tt0162222", 1), ("tt0421715", 1), ("tt0499549", -1),
      ("tt1302006", 1), ("tt3460252", 1), ("tt0151804", 1), ("tt1490017", 1), ("tt0079116", 1), ("tt0099674", 1),
      ("tt0215750", 1), ("tt0416449", 1), ("tt0829482", 1), ("tt1403865", 1), ("tt2582782", 1), ("tt3397884", 1),
      ("tt3890160", -1), ("tt0373889", 1), ("tt0071877", -1), ("tt0120855", 1), ("tt0462538", 1), ("tt1596343", -1),
      ("tt1670345", -1), ("tt3183660", -1), ("tt0298148", 1), ("tt1392170", -1), ("tt0298203", 1), ("tt0116629", -1),
      ("tt2294449", 1), ("tt0109040", 1), ("tt0496806", 1), ("tt4520988", -1), ("tt0167190", -1), ("tt0364725", 1),
      ("tt1270797", -1), ("tt0141926", 1), ("tt1840309", -1), ("tt0356910", 1), ("tt3110958", -1), ("tt0112281", 1),
      ("tt0252866", 1), ("tt0366548", -1), ("tt0389860", 1), ("tt2872732", -1), ("tt0213149", 1), ("tt0335438", 1),
      ("tt1323045", 1), ("tt0322259", -1), ("tt0349205", -1), ("tt0450278", -1), ("tt1386697", -1),
      ("tt1389072", -1), ("tt0242423", 1), ("tt0960144", 1), ("tt0377818", 1)},

  al={("tt0068646", -1), ("tt0071562", -1), ("tt0110912", -1), ("tt0167260", 1), ("tt0109830", 1), ("tt0120737", 1),
      ("tt0167261", 1), ("tt0047478", 1), ("tt0120689", -1), ("tt0245429", -1), ("tt0816692", 1), ("tt0110357", -1),
      ("tt0110413", -1), ("tt0253474", 1), ("tt0119698", 1), ("tt0086879", 1), ("tt0211915", 1), ("tt0042876", 1),
      ("tt0096283", 1), ("tt0347149", 1), ("tt0434409", -1), ("tt0031381", 1), ("tt0112471", 1), ("tt1201607", -1),
      ("tt0046250", -1), ("tt0059742", 1), ("tt0107688", 1), ("tt0304141", 1), ("tt2209418", 1), ("tt0058331", 1),
      ("tt0058385", 1), ("tt0067992", -1), ("tt0079417", 1), ("tt0903624", 1), ("tt1170358", 1), ("tt3281548", -1),
      ("tt0093389", 1), ("tt0301357", 1), ("tt0330373", 1), ("tt0926084", -1), ("tt1022603", -1), ("tt0070608", 1),
      ("tt0099674", -1), ("tt0241527", 1), ("tt0314331", 1), ("tt0417741", -1), ("tt0450188", 1), ("tt0983213", 1),
      ("tt0373889", 1), ("tt0084805", 1), ("tt0238380", 1), ("tt0295297", 1), ("tt0397535", -1), ("tt2310332", 1),
      ("tt4555426", 1), ("tt0110367", -1), ("tt0383574", -1), ("tt1229822", 1), ("tt3183660", -1), ("tt0241303", 1),
      ("tt0293508", -1), ("tt1392170", -1), ("tt2080374", -1), ("tt0120102", 1), ("tt0449088", -1),
      ("tt0100405", -1), ("tt0431308", -1), ("tt0335119", 1), ("tt0363771", -1), ("tt0458352", 1), ("tt0316396", 1),
      ("tt0107977", 1), ("tt0243155", -1), ("tt1972591", -1), ("tt0955308", 1), ("tt1298650", -1), ("tt1781769", -1),
      ("tt1951265", -1), ("tt1951266", -1), ("tt6911608", -1), ("tt0304415", -1), ("tt3794354", -1),
      ("tt0795421", 1), ("tt1509767", -1), ("tt1673434", -1), ("tt1686821", -1), ("tt1325004", -1),
      ("tt1991245", -1), ("tt1324999", 1), ("tt1259571", -1), ("tt4465564", -1), ("tt1261945", -1),
      ("tt2322441", -1), ("tt0312528", 1),
      ("tt1702443", -1)}
  ,
  el={("tt0108052", 1), ("tt0167260", 1), ("tt0060196", 1), ("tt0120737", 1), ("tt0073486", 1), ("tt0133093", 1),
      ("tt0167261", 1), ("tt6751668", -1), ("tt0034583", 1), ("tt0054215", 1), ("tt0064116", 1), ("tt0110357", 1),
      ("tt0082971", 1), ("tt0087843", 1), ("tt4154756", -1), ("tt0056172", 1), ("tt0180093", 1), ("tt0211915", 1),
      ("tt0040897", 1), ("tt0059578", 1), ("tt0071853", 1), ("tt0095016", 1), ("tt0096283", 1), ("tt0097576", 1),
      ("tt0347149", 1), ("tt0031381", 1), ("tt0087544", 1), ("tt0107290", 1), ("tt0266543", 1), ("tt1201607", -1),
      ("tt0058461", 1), ("tt0073195", 1), ("tt0088247", 1), ("tt0101414", 1), ("tt0103639", 1), ("tt0325980", 1),
      ("tt0013442", 1), ("tt0304141", 1), ("tt0067992", -1), ("tt0120338", 1), ("tt0126029", 1), ("tt0499549", 1),
      ("tt0903624", 1), ("tt0325710", 1), ("tt0330373", 1), ("tt0851578", 1), ("tt0926084", -1), ("tt0029583", 1),
      ("tt0097757", 1), ("tt0099785", 1), ("tt0120669", 1), ("tt0120762", 1), ("tt0241527", 1), ("tt0416449", 1),
      ("tt0417741", -1), ("tt0087469", 1), ("tt0373889", 1), ("tt0295297", 1), ("tt0042332", 1), ("tt0048280", 1),
      ("tt0055254", 1), ("tt0071877", 1), ("tt0120855", 1), ("tt0443453", 1), ("tt0053285", 1), ("tt0118617", 1),
      ("tt0298203", 1), ("tt0116583", 1), ("tt0109040", 1), ("tt0088944", 1), ("tt0115641", 1), ("tt0114148", 1),
      ("tt0338348", 1), ("tt1781769", 1), ("tt0120915", 1), ("tt0121765", 1), ("tt0112281", 1), ("tt0151137", -1),
      ("tt1014759", 1), ("tt0212346", 1), ("tt0164912", 1), ("tt0103786", 1), ("tt0346491", -1), ("tt1673434", -1),
      ("tt1099212", -1), ("tt4465564", -1), ("tt4477536", -1), ("tt2322441", -1)},

  ri={("tt0111161", 1), ("tt0068646", 1), ("tt0071562", 1), ("tt0110912", 1), ("tt0167260", 1), ("tt0109830", 1),
      ("tt0120737", 1), ("tt0080684", 1), ("tt0099685", -1), ("tt0133093", 1), ("tt0167261", 1), ("tt0047478", 1),
      ("tt0076759", 1), ("tt0120689", -1), ("tt0120815", -1), ("tt0816692", -1), ("tt6751668", -1),
      ("tt0110357", 1), ("tt1675434", 1), ("tt0082971", 1), ("tt0910970", 1), ("tt1345836", 1), ("tt1853728", 1),
      ("tt2380307", 1), ("tt7286456", -1), ("tt0086190", 1), ("tt0114709", 1), ("tt0361748", 1), ("tt0050986", -1),
      ("tt0071853", 1), ("tt0075314", 1), ("tt0097576", 1), ("tt0372784", 1), ("tt0993846", 1), ("tt0075148", 1),
      ("tt0083658", -1), ("tt0107290", 1), ("tt0198781", 1), ("tt0266543", 1), ("tt0892769", 1), ("tt1201607", 1),
      ("tt12361974", -1), ("tt1392190", 1), ("tt2119532", 1), ("tt3315342", 1), ("tt0088247", 1), ("tt0129167", 1),
      ("tt0317705", 1), ("tt0325980", 1), ("tt0848228", 1), ("tt1431045", 1), ("tt1727824", 1), ("tt2015381", 1),
      ("tt2084970", 1), ("tt2948356", 1), ("tt3783958", -1), ("tt0094226", 1), ("tt0304141", 1), ("tt0371746", 1),
      ("tt0796366", 1), ("tt1877832", 1), ("tt2488496", 1), ("tt3501632", 1), ("tt0067992", 1), ("tt0087332", 1),
      ("tt0093773", 1), ("tt0120338", 1), ("tt0126029", 1), ("tt0499549", 1), ("tt0903624", 1), ("tt1074638", 1),
      ("tt1170358", 1), ("tt1646971", 1), ("tt3498820", 1), ("tt0330373", 1), ("tt0373074", 1), ("tt0926084", 1),
      ("tt1270798", 1), ("tt1843866", 1), ("tt2802144", 1), ("tt5463162", 1)}
)


def getRandomSample(population):
  sampleProportion = (random.random() * 0.3) + 0.1
  sampleSize = max(int(len(population) * sampleProportion), min(10, len(population)))
  return random.sample(population, sampleSize)


def ratingToEnum(rating: int) -> str:
  return "LIKED" if rating == 1 else "DISLIKED"


if __name__ == '__main__':
  prototypes = list(ratings.keys())
  num_users = 20

  realUsers = [
    User(1, "juraj", "juraj@muni.cz", "", True),
    User(2, "elena", "elena@muni.cz", "", True),
    User(3, "alina", "alina@muni.cz", "", True),
    User(4, "richard", "richard@muni.cz", "", True)
  ]

  randUsers = [User(i, username := f"{random.choice(prototypes)}user{i}", f"{username}@muni.cz", "", False) for i in
               range(5, 5 + num_users)]

  randRatings = [Rating(user.id, int(rating[0][2:]), ratingToEnum(rating[1]))
                 for user in randUsers for rating in getRandomSample(ratings.get(user.name[:2]))]

  realRatings = [Rating(user.id, int(rating[0][2:]), ratingToEnum(rating[1]))
                 for user in realUsers for rating in ratings.get(user.name[:2])]

  allusers = randUsers + realUsers
  allratings = realRatings + randRatings

  writeUsers(allusers)
  writeUserRatings(allratings)
